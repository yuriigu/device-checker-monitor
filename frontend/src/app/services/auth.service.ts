import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

export interface LoginResponse {
  token: string;
  name: string;
  email: string;
  role: string;
  expiresIn: number;
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

  logout(): void {
    this.clearToken();
    void this.router.navigateByUrl('/login');
  }

  private clearToken(): void {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem(this.tokenKey);
      localStorage.removeItem(this.expirationKey);
    }
  }
}
