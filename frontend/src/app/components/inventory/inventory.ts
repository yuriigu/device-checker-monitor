import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeviceService } from '../../../services/device.service';
import { UserService } from '../../../services/user.service';

export interface DeviceSummary {
  id: number;
  name: string;
  ipAddress: string;
  macAddress: string;
  brandName: string;
  modelName: string;
  placeName: string;
}

export interface UserSummary {
  id: number;
  name: string;
  email: string;
  role: string;
}

export interface ReportsSummary {
  criticalAlerts: number;
  offlineDevices: number;
  reportsGenerated: number;
  uptime: string;
}

@Component({
  selector: 'app-inventory',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './inventory.html',
  styleUrl: './inventory.css',
})
export class InventoryComponent implements OnInit {

  private readonly deviceService = inject(DeviceService);
  private readonly userService = inject(UserService);

  devices: DeviceSummary[] = [];
  users: UserSummary[] = [];
  reports: ReportsSummary = {
    criticalAlerts: 0,
    offlineDevices: 0,
    reportsGenerated: 0,
    uptime: '0%',
  };

  ngOnInit(): void {
    this.loadDevices();
    this.loadUsers();
    this.loadReports();
  }

  loadDevices(): void {
    this.deviceService.getDevices().subscribe({
      next: (data: any) => {
        this.devices = data;
      },
      error: (err: any) => {
        console.error('Erro ao carregar dispositivos:', err);
      },
    });
  }

  loadUsers(): void {
    this.userService.findAll().subscribe({
      next: (data: any) => {
        this.users = data;
      },
      error: (err: any) => {
        console.error('Erro ao carregar usuários:', err);
      },
    });
  }

  loadReports(): void {
    this.reports = {
      criticalAlerts: 3,
      offlineDevices: 2,
      reportsGenerated: 15,
      uptime: '99.8%',
    };
  }
}
