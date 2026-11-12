import { Routes } from '@angular/router';
import { DeviceForm } from './components/device-form/device-form';
import { DashboardComponent } from './components/dashboard/dashboard';
import { AlertsComponent } from './components/alerts/alerts';
import { InventoryComponent } from './components/inventory/inventory';
import { ReportsComponent } from './components/reports/reports';
import { UsersComponent } from './components/users/users';
import { SettingsComponent } from './components/settings/settings';
import { LoginComponent } from './components/login/login';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full'},
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard]},
  { path: 'dispositivos', component: DeviceForm, canActivate: [authGuard]},
  { path: 'alertas', component: AlertsComponent, canActivate: [authGuard]},
  { path: 'inventario', component: InventoryComponent, canActivate: [authGuard]},
  { path: 'reports', component: ReportsComponent, canActivate: [authGuard]},
  { path: 'usuarios', component: UsersComponent, canActivate: [authGuard]},
  { path: 'settings', component: SettingsComponent, canActivate: [authGuard]},
  { path: '**', redirectTo: 'dashboard'}
];
