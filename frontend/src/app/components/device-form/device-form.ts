import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgClass } from '@angular/common';
import { DeviceService } from '../../services/device.service';

@Component({
  imports: [ReactiveFormsModule, NgClass],
  selector: 'app-device-form',
  styleUrl: './device-form.css',
  templateUrl: './device-form.html',
})
export class DeviceForm {
  deviceForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private deviceService: DeviceService,
  ) {
    this.deviceForm = this.fb.group({
      name: ['', Validators.required],
      idBrand: ['', Validators.required],
      idModel: [''],
      ipAddress: ['', Validators.required],
      macAddress: [''],
      idRole: ['', Validators.required],
      idPlace: ['', Validators.required],
    })
  }

  onSubmit() {
    if (this.deviceForm.valid) {
      const formValues = this.deviceForm.value;

      const payload: any = {
        name: formValues.name,
        idBrand: Number(formValues.idBrand),
        ipAddress: formValues.ipAddress,
        idPlace: Number(formValues.idPlace),
        deviceRoles: [
          {
            idRole: Number(formValues.idRole),
            timeStart: '08:00:00',
            timeEnd: '18:00:00',
            active: true,
          },
        ],
      };

      if (formValues.idModel !== null && formValues.idModel !== '' && !isNaN(formValues.idModel)) {
        payload.idModel = Number(formValues.idModel);
      }

      if (formValues.macAddress && formValues.macAddress.trim() !== '') {
        payload.macAddress = formValues.macAddress.trim();
      } else {
        payload.macAddress = null;
      }

      console.log('Payload final enviado para o backend:', payload);

      this.deviceService.createDevice(payload).subscribe({
        next: (response) => {
          console.log('Dispositivo cadastrado com sucesso!', response);
          alert('Dispositivo cadastrado com sucesso!');
          this.deviceForm.reset();
        },
        error: (err) => {
          console.error('Erro detalhado retornado pelo backend:', err);
          const errorMsg = err.error?.details
            ? err.error.details.join(', ')
            : err.error?.message || 'Erro desconhecido';
          alert('Erro ao cadastrar: ' + errorMsg);
        },
      });
    } else {
      console.log('Formulário inválido.');
      alert('Por favor, preencha todos os campos obrigatórios (*).');
      this.deviceForm.markAllAsTouched();
    }
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.deviceForm.get(fieldName);
    return !!(field && field.invalid && (field.touched || field.dirty));
  }
}
