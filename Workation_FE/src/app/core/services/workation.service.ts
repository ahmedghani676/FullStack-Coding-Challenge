import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';

import { ApiResponse } from '../models/api-response.model';
import { SortDirection } from '../models/sort.model';
import { Workation } from '../models/workation.model';

/**
 * Data-access for workations. Talks to GET /workflex/workation and unwraps the
 * {@link ApiResponse} envelope so components work with plain {@link Workation}s.
 * Sorting is delegated to the backend via the `sortBy`/`direction` query params.
 */
@Injectable({ providedIn: 'root' })
export class WorkationService {
  private static readonly ENDPOINT = '/workflex/workation';

  private readonly http = inject(HttpClient);

  getWorkations(sortBy?: string, direction?: SortDirection): Observable<Workation[]> {
    let params = new HttpParams();
    if (sortBy) {
      params = params.set('sortBy', sortBy);
    }
    if (direction) {
      params = params.set('direction', direction);
    }
    return this.http
      .get<ApiResponse<Workation[]>>(WorkationService.ENDPOINT, { params })
      .pipe(map((response) => response.data));
  }
}
