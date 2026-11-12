import { Routes } from '@angular/router';
import { DeviceForm } from './components/device-form/device-form';
import { DashboardComponent } from './components/dashboard/dashboard';
import { AlertsComponent } from './components/alerts/alerts';
import { InventoryComponent } from './components/inventory/inventory';
import { ReportsComponent } from './components/reports/reports';
import { UsersComponent } from './components/users/users';
import { SettingsComponent } from './components/settings/settings';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full'},
  { path: 'dashboard', component: DashboardComponent},
  { path: 'dispositivos', component: DeviceForm},
  { path: 'alertas', component: AlertsComponent},
  { path: 'inventario', component: InventoryComponent},
  { path: 'reports', component: ReportsComponent},
  { path: 'usuarios', component: UsersComponent},
  { path: 'settings', component: SettingsComponent},
  { path: '**', redirectTo: 'dashboard'}
];
