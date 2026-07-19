import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideNoopAnimations } from '@angular/platform-browser/animations';

import { ApiResponse } from '../../core/models/api-response.model';
import { RiskLevel, Workation } from '../../core/models/workation.model';
import { WorkationTableComponent } from './workation-table.component';

describe('WorkationTableComponent', () => {
  let httpMock: HttpTestingController;

  const workation = (over: Partial<Workation> = {}): Workation => ({
    workationId: 'w1',
    employee: 'Steffen Jacobs',
    origin: 'Germany',
    destination: 'United States',
    startDate: '2024-01-02',
    endDate: '2024-12-31',
    workingDays: 65,
    risk: RiskLevel.High,
    ...over,
  });

  const ok = (data: Workation[]): ApiResponse<Workation[]> => ({
    status: 200,
    message: 'OK',
    data,
  });

  // Lets the rxResource effect run (firing / re-firing the request) without waiting
  // on the mocked HTTP response, which whenStable() would block on.
  const tick = () => new Promise<void>((resolve) => setTimeout(resolve));

  // Material renders a clickable container per sortable header, in column order.
  const sortHeaders = (fixture: ComponentFixture<WorkationTableComponent>): HTMLElement[] =>
    Array.from(fixture.nativeElement.querySelectorAll('.mat-sort-header-container'));

  const initComponent = async (): Promise<ComponentFixture<WorkationTableComponent>> => {
    const fixture = TestBed.createComponent(WorkationTableComponent);
    fixture.detectChanges();
    await tick();
    return fixture;
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [WorkationTableComponent],
      providers: [provideHttpClient(), provideHttpClientTesting(), provideNoopAnimations()],
    });
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('fetches in CSV order by default (workationId, asc) and renders a row per workation', async () => {
    const fixture = await initComponent();

    const req = httpMock.expectOne((r) => r.url === '/workflex/workation');
    expect(req.request.params.get('sortBy')).toBe('workationId');
    expect(req.request.params.get('direction')).toBe('asc');
    req.flush(ok([workation(), workation({ workationId: 'w2', employee: 'Ayushi Singh' })]));
    await tick();
    fixture.detectChanges();

    expect(fixture.nativeElement.querySelectorAll('.workation-table__row')).toHaveLength(2);
  });

  it('formats dates as dd/MM/yyyy', async () => {
    const fixture = await initComponent();
    httpMock.expectOne((r) => r.url === '/workflex/workation').flush(ok([workation()]));
    await tick();
    fixture.detectChanges();

    const dates = fixture.nativeElement.querySelectorAll('.cell--date');
    expect((dates[0] as HTMLElement).textContent?.trim()).toBe('02/01/2024');
    expect((dates[1] as HTMLElement).textContent?.trim()).toBe('31/12/2024');
  });

  it('re-fetches with the chosen field when a column header is clicked', async () => {
    const fixture = await initComponent();
    httpMock.expectOne((r) => r.url === '/workflex/workation').flush(ok([workation()]));
    await tick();
    fixture.detectChanges();

    // Column order: Employee, Origin, Destination, Start, End, Working days, Risk
    sortHeaders(fixture)[5].click();
    await tick();

    const req = httpMock.expectOne(
      (r) => r.params.get('sortBy') === 'workingDays' && r.params.get('direction') === 'asc',
    );
    req.flush(ok([workation()]));
    await tick();
  });

  it('toggles direction to desc when the same header is clicked again', async () => {
    const fixture = await initComponent();
    httpMock.expectOne((r) => r.params.get('sortBy') === 'workationId').flush(ok([workation()]));
    await tick();
    fixture.detectChanges();

    sortHeaders(fixture)[0].click(); // Employee -> ascending
    await tick();
    httpMock
      .expectOne((r) => r.params.get('sortBy') === 'employee' && r.params.get('direction') === 'asc')
      .flush(ok([workation()]));
    await tick();

    sortHeaders(fixture)[0].click(); // Employee again -> descending
    await tick();
    httpMock
      .expectOne(
        (r) => r.params.get('sortBy') === 'employee' && r.params.get('direction') === 'desc',
      )
      .flush(ok([workation()]));
    await tick();
  });

  it('shows an error message when the request fails', async () => {
    const fixture = await initComponent();
    httpMock
      .expectOne((r) => r.url === '/workflex/workation')
      .flush('boom', { status: 500, statusText: 'Server Error' });
    await tick();
    fixture.detectChanges();

    expect((fixture.nativeElement as HTMLElement).querySelector('.state--error')).toBeTruthy();
  });
});
