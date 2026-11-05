import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { DeviceForm } from './components/device-form/device-form';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [SidebarComponent, DeviceForm],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class AppComponent {
  title = 'dcm-web';
}
