import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, of, tap, catchError, exhaustMap, map } from 'rxjs';

export interface LoginResponse {
  token: string;
  name: string;
  email: string;
  role: string;
  expiresIn: number;
}

export interface RefreshTokenResponse {
  token: string;
  message: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly apiUrl = '/api/auth';
  private readonly tokenKey = 'access_token';
  private readonly expirationKey = 'access_token_expires_at';
  private readonly refreshBufferMs = 5 * 60 * 1000; // 5 minutes buffer before expiry

  login(login: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, { login, password }).pipe(
      tap((response) => {
        if (typeof localStorage !== 'undefined') {
          localStorage.setItem(this.tokenKey, response.token);
          localStorage.setItem(
            this.expirationKey,
            String(Date.now() + response.expiresIn * 1_000),
          );
        }
      }),
      catchError((error) => {
        this.clearToken();
        return [];
      })
    );
  }

  getToken(): string | null {
    if (typeof localStorage === 'undefined') {
      return null;
    }

    const token = localStorage.getItem(this.tokenKey);
    const expiresAt = Number(localStorage.getItem(this.expirationKey));

    if (!token || !expiresAt || expiresAt <= Date.now()) {
      this.clearToken();
      return null;
    }

    return token;
  }

  isAuthenticated(): boolean {
    return this.getToken() !== null;
  }

  refreshTokenIfNeeded(): Observable<boolean> {
    const token = this.getToken();
    if (!token) {
      return of(false);
    }

    // Check if token expires within the buffer period
    const expiresAt = Number(localStorage.getItem(this.expirationKey) || '0');
    const now = Date.now();
    const timeUntilExpiry = expiresAt - now;

    if (timeUntilExpiry > this.refreshBufferMs) {
      // Token is valid for more than the buffer, no need to refresh
      return of(true);
    }

    // Try to refresh the token
    return this.http.post<RefreshTokenResponse>(`${this.apiUrl}/refresh-token`, {
      token,
    }).pipe(
      tap((response) => {
        if (response && response.token) {
          localStorage.setItem(this.tokenKey, response.token);
          // Set new expiration (assuming same expiration time)
          localStorage.setItem(
            this.expirationKey,
            String(Date.now() + this.getDefaultExpiration() * 1_000),
          );
        }
      }),
      map(() => true),
      catchError((error) => {
        // Token refresh failed, clear tokens and redirect to login
        this.clearToken();
        this.router.navigateByUrl('/login');
        return of(false);
      })
    );
  }

  private getDefaultExpiration(): number {
    // Try to get from localStorage or use default (1 hour)
    const stored = localStorage.getItem('jwt_expiration_seconds');
    if (stored) {
      return Number(stored);
    }
    return 3600; // 1 hour default
  }

  logout(): void {
    this.clearToken();
    void this.router.navigateByUrl('/login');
  }

  private clearToken(): void {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem(this.tokenKey);
      localStorage.removeItem(this.expirationKey);
      localStorage.removeItem('jwt_expiration_seconds');
    }
  }
}
