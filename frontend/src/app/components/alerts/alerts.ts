import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
  AbstractControl,
  ValidationErrors,
} from '@angular/forms';
import { UserService } from '../../services/user.service';
import { ValidationError } from '@angular/forms/signals';

@Component({
  selector: 'app-alerts',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './alerts.html',
  styleUrl: './alerts.css',
})

export class AlertsComponent {
  UsersComponent: FormGroup;
  isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private alertsService: AlertsService
    ) {
    this.AlertsComponent = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      severity: ['', Validator.required],
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.UsersComponent.get(fieldName);
    return !!(field && field.invalid && (field.dirty || field.touched));
  }

  fieldClass(fieldName: string): string {
    const base =
      'mt-1 block w-full rounded-md border bg-slate-950 px-3 py-2 text-sm text-slate-100 placeholder-slate-500 focus:outline-none focus:ring-2';
    return this.isFieldInvalid(fieldName)
      ? `${base} border-red-500 focus:ring-red-500`
      : `${base} border-slate-700 focus:ring-amber-400`;
  }

  onSubmit() {
    if (this.AlertsComponent.invalid) {
      this.AlertsComponent.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    const { formData } = this.AlertsComponent.value;

    this.alertsService.createAlert(formData).subscribe({
      next: (response) => {
        console.log('Alerta criado com sucesso!', response);
        this.isSubmitting = false;
        this.AlertsComponent.reset();
      },
      error: (err) => {
        console.error('Erro ao criar alerta:', err);
        this.isSubmitting = false;
      },
    });
  }

  onCancel() {
    this.AlertComponent.reset();
  }
}
