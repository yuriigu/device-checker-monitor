import { Routes } from '@angular/router';
import { DeviceForm } from './components/device-form/device-form';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full'},
  { path: 'dashboard', component: DeviceForm},
  { path: 'dispositivos', component: DeviceForm},
  { path: 'alertas', component: DeviceForm},
  { path: 'inventario', component: DeviceForm},
  { path: 'reports', component: DeviceForm},
  { path: 'usuarios', component: DeviceForm},
  { path: 'settings', component: DeviceForm},
  { path: '**', redirectTo: 'dashboard'}
];
