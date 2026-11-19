import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RulesService {
  private readonly http = inject(HttpClient);

  private readonly apiUrl = '/api/rules';

  constructor() {}

  createRule(ruleData: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, ruleData);
  }
}
