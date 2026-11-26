import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})

export class DeviceService {
  private readonly http = inject(HttpClient);

  private readonly apiUrl = '/api/devices';

  constructor() {}

  createDevice(deviceData: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, deviceData);
  }

  getDevices(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }
}
