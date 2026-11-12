import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Validators, FormGroups, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './users.html',
  styleUrl: './users.css',
})
export class UsersComponent {
  UsersComponent: FormGroups;
  constructor(){
    name: ['', Validators.required],
    email: ['', Validators.required],
    password: ['', Validators.required],
  }
