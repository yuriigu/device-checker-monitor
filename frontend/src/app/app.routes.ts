import { Routes } from '@angular/router';
import { DeviceForm } from './components/device-form/device-form';

export const routes: Routes = [
  { path: '', redirectTo: 'dispositivos', pathMatch: 'full'},
  { path: 'dispositivos', component: DeviceForm},
];
