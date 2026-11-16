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
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './users.html',
  styleUrl: './users.css',
})

export class UsersComponent {
  UsersComponent: FormGroup;
  isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private userService: UserService
    ) {
    this.UsersComponent = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
    }, {validators: this.passwordMatchValidator});
  }

  passwordMatchValidator(control: AbstractControl): ValidationErrors | null{
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');

    if(password && confirmPassword && password.value !== confirmPassword.value){
      confirmPassword.setErrors({mismatch: true});
      return {mismatch: true};
    }
    return null;
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
    if (this.UsersComponent.invalid) {
      this.UsersComponent.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    const { confirmPassword, formData } = this.UsersComponent.value;

    this.userService.createUser(formData).subscribe({
      next: (response) => {
        console.log('Usuário criado com sucesso!', response);
        this.isSubmitting = false;
        this.UsersComponent.reset();
      },
      error: (err) => {
        console.error('Erro ao criar usuário:', err);
        this.isSubmitting = false;
      },
    });
  }

  onCancel() {
    this.UsersComponent.reset();
  }
}
