import { Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DeviceService } from '../../services/device.service';

const IP_PATTERN = /^((25[0-5]|2[0-4]\d|1?\d?\d)\.){3}(25[0-5]|2[0-4]\d|1?\d?\d)$/;
const MAC_PATTERN = /^([0-9A-Fa-f]{2}[:-]?){5}[0-9A-Fa-f]{2}$/;

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-device-form',
  styleUrl: './device-form.css',
  templateUrl: './device-form.html',
})
export class DeviceForm {
  deviceForm: FormGroup;
  isSubmitting = signal(false);

  private readonly baseInputClass =
    'mt-1 block w-full rounded-md border bg-slate-950 px-3 py-2 text-sm text-slate-100 ' +
    'placeholder-slate-500 focus:outline-none focus:ring-1 focus:ring-amber-400';

  constructor(
    private fb: FormBuilder,
    private deviceService: DeviceService,
  ) {
    this.deviceForm = this.fb.group({
      name: ['', Validators.required],
      idBrand: [null, Validators.required],
      idModel: [null],
      ipAddress: ['', [Validators.required, Validators.pattern(IP_PATTERN)]],
      macAddress: ['', Validators.pattern(MAC_PATTERN)],
      idPlace: [null, Validators.required],
      idRole: [null, Validators.required],
    });
  }

  onSubmit() {
    if (this.deviceForm.invalid) {
      this.deviceForm.markAllAsTouched();
      return;
    }

    const formValues = this.deviceForm.value;

    const payload: Record<string, unknown> = {
      name: formValues.name.trim(),
      idBrand: Number(formValues.idBrand),
      ipAddress: formValues.ipAddress.trim(),
      idPlace: Number(formValues.idPlace),
      macAddress: null,
      deviceRoles: [
        {
          idRole: Number(formValues.idRole),
          timeStart: '08:00:00',
          timeEnd: '18:00:00',
          active: true,
        },
      ],
    };

    if (formValues.idModel) {
      payload['idModel'] = Number(formValues.idModel);
    }

    if (formValues.macAddress && formValues.macAddress.trim() !== '') {
      payload['macAddress'] = formValues.macAddress.trim();
    }

    console.log('Payload final enviado para o backend:', payload);

    this.isSubmitting.set(true);
    this.deviceService.createDevice(payload).subscribe({
      next: (response) => {
        console.log('Dispositivo cadastrado com sucesso!', response);
        alert('Dispositivo cadastrado com sucesso!');
        this.deviceForm.reset();
        this.isSubmitting.set(false);
      },
      error: (err) => {
        console.error('Erro detalhado retornado pelo backend:', err);
        const errorMsg = err.error?.details
          ? err.error.details.join(', ')
          : err.error?.message || 'Erro desconhecido';
        alert('Erro ao cadastrar: ' + errorMsg);
        this.isSubmitting.set(false);
      },
    });
  }

  onCancel() {
    this.deviceForm.reset();
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.deviceForm.get(fieldName);
    return !!(field && field.invalid && (field.touched || field.dirty));
  }

  fieldClass(fieldName: string): string {
    if (this.isFieldInvalid(fieldName)) {
      return this.baseInputClass + ' border-red-500';
    }
    return this.baseInputClass + ' border-slate-700';
  }
}
