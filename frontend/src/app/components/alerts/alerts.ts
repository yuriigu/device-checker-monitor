import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-alerts',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './alerts.html',
  styleUrl: './alerts.css',
})

export class AlertsComponent {
  constructor(){
    name: ['', Validators.required],
    description: ['', Validators.required],
    }
  }
