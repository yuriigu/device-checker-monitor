import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/users';

  constructor() {}

  createUser(userData: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, userData);
  }
}
