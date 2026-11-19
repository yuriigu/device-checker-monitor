import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DeviceService {
  private readonly http = inject(HttpClient);

  // O Express/SSR encaminha /api para API_URL; o padrao local e localhost:8080.
  private readonly apiUrl = '/api/devices';

  constructor() {}

  createDevice(deviceData: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, deviceData);
  }
}
