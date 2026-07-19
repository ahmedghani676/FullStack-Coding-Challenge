import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ApiResponse } from '../models/api-response.model';
import { SortDirection } from '../models/sort.model';
import { RiskLevel, Workation } from '../models/workation.model';
import { WorkationService } from './workation.service';

describe('WorkationService', () => {
  let service: WorkationService;
  let httpMock: HttpTestingController;

  const sample: Workation = {
    workationId: 'w1',
    employee: 'Steffen Jacobs',
    origin: 'Germany',
    destination: 'United States',
    startDate: '2024-01-02',
    endDate: '2024-12-31',
    workingDays: 65,
    risk: RiskLevel.High,
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(WorkationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('unwraps the ApiResponse envelope and returns the data array', () => {
    let result: Workation[] | undefined;
    service.getWorkations().subscribe((data) => (result = data));

    const req = httpMock.expectOne('/workflex/workation');
    expect(req.request.method).toBe('GET');
    const body: ApiResponse<Workation[]> = { status: 200, message: 'OK', data: [sample] };
    req.flush(body);

    expect(result).toEqual([sample]);
  });

  it('forwards sortBy and direction as query params', () => {
    service.getWorkations('workingDays', SortDirection.Desc).subscribe();

    const req = httpMock.expectOne(
      (r) =>
        r.url === '/workflex/workation' &&
        r.params.get('sortBy') === 'workingDays' &&
        r.params.get('direction') === 'desc',
    );
    req.flush({ status: 200, message: 'OK', data: [] });
  });

  it('omits query params when no sort is provided', () => {
    service.getWorkations().subscribe();

    const req = httpMock.expectOne('/workflex/workation');
    expect(req.request.params.keys()).toHaveLength(0);
    req.flush({ status: 200, message: 'OK', data: [] });
  });
});
