import { Routes } from '@angular/router';
import { DeviceForm } from './components/device-form/device-form';
import { DashboardComponent } from './components/dashboard/dashboard';
import { RulesComponent } from './components/rules/rules';
import { InventoryComponent } from './components/inventory/inventory';
import { ReportsComponent } from './components/reports/reports';
import { UsersComponent } from './components/users/users';
import { SettingsComponent } from './components/settings/settings';
import { LoginComponent } from './components/login/login';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full'},
  { path: 'dashboard', component: DashboardComponent},
  { path: 'dispositivos', component: DeviceForm},
  { path: 'regras', component: RulesComponent},
  { path: 'inventario', component: InventoryComponent},
  { path: 'reports', component: ReportsComponent},
  { path: 'usuarios', component: UsersComponent},
  { path: 'settings', component: SettingsComponent},
  { path: '**', redirectTo: 'dashboard'}
];
