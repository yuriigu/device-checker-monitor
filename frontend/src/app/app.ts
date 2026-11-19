import { Component, inject } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { filter, map, startWith, exhaustMap } from 'rxjs';

import { AuthService } from './services/auth.service';
import { SidebarComponent } from './components/sidebar/sidebar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [SidebarComponent, RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class AppComponent {
  title = 'dcm-web';
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);

  readonly showSidebar = toSignal(
    this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd),
      map((event) => event.urlAfterRedirects !== '/login'),
      startWith(this.router.url !== '/login'),
    ),
    { initialValue: this.router.url !== '/login' }
  );

  constructor() {
    // Try to refresh token on app initialization if user is authenticated
    this.tryRefreshTokenOnInit();
  }

  private tryRefreshTokenOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.authService.refreshTokenIfNeeded().subscribe({
        next: (refreshed) => {
          console.log('Token refresh result:', refreshed);
        },
        error: (error) => {
          console.error('Token refresh failed on init:', error);
        },
      });
    }
  }
}