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
import { RulesService } from '../../services/rules.service';

@Component({
  selector: 'app-rules',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './rules.html',
  styleUrl: './rules.css',
})

export class RulesComponent {
  RulesComponent: FormGroup;
  isSubmitting = false;

  severities = [
    {label: 'Informativo', value: 'informativo'},
    {label: 'Aviso', value: 'aviso'},
    {label: 'Crítico', value: 'crítico'},
  ];
  constructor(
    private fb: FormBuilder,
    private rulesService: RulesService
    ) {
    this.RulesComponent = this.fb.group({
      name: ['', Validators.required],
      user: ['', Validators.required],
      occurrence: ['', Validators.required],
      severity: ['', Validators.required],
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.RulesComponent.get(fieldName);
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
    if (this.RulesComponent.invalid) {
      this.RulesComponent.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    this.rulesService.createRule(this.RulesComponent.value).subscribe({
      next: (response) => {
        console.log('Regra criada com sucesso!', response);
        this.isSubmitting = false;
        this.RulesComponent.reset();
      },
      error: (err) => {
        console.error('Erro ao criar regra:', err);
        this.isSubmitting = false;
      },
    });
  }

  onCancel() {
    this.RulesComponent.reset();
  }
}
