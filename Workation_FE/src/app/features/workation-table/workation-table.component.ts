import { DatePipe, NgTemplateOutlet } from '@angular/common';
import { ChangeDetectionStrategy, Component, computed, inject, signal } from '@angular/core';
import { rxResource } from '@angular/core/rxjs-interop';
import { MatSortModule, Sort } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';

import { SortableColumn, SortDirection } from '../../core/models/sort.model';
import { Workation } from '../../core/models/workation.model';
import { WorkationService } from '../../core/services/workation.service';
import { LocationCellComponent } from '../../shared/components/location-cell/location-cell.component';
import { RiskBadgeComponent } from '../../shared/components/risk-badge/risk-badge.component';

/** Order the table falls back to when no column is actively sorted (CSV order). */
const DEFAULT_SORT = { field: 'workationId', direction: SortDirection.Asc } as const;

/**
 * Renders the workations table with Angular Material (`mat-table` + `matSort`).
 *
 * <p>Data loading is fully reactive: the sort field/direction are signals, and an
 * {@link rxResource} re-fetches from the backend automatically whenever they change.
 * There is no lifecycle hook or manual subscription — the resource owns the request
 * lifecycle (including teardown), and exposes value/loading/error as signals. Sorting
 * stays delegated to the backend (the server is the single source of truth).
 */
@Component({
  selector: 'app-workation-table',
  imports: [
    MatTableModule,
    MatSortModule,
    NgTemplateOutlet,
    DatePipe,
    LocationCellComponent,
    RiskBadgeComponent,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './workation-table.component.html',
  styleUrl: './workation-table.component.scss',
})
export class WorkationTableComponent {
  /** Columns in screenshot order; `field` doubles as the matColumnDef / sort id. */
  protected readonly columns: readonly SortableColumn[] = [
    { header: 'Employee', field: 'employee' },
    { header: 'Origin', field: 'origin' },
    { header: 'Destination', field: 'destination' },
    { header: 'Start', field: 'startDate' },
    { header: 'End', field: 'endDate' },
    { header: 'Working days', field: 'workingDays', numeric: true },
    { header: 'Risk', field: 'risk' },
  ];

  protected readonly displayedColumns = this.columns.map((column) => column.field);

  private readonly sortBy = signal<string>(DEFAULT_SORT.field);
  private readonly direction = signal<SortDirection>(DEFAULT_SORT.direction);

  private readonly workationService = inject(WorkationService);

  /** Re-fetches automatically whenever the sort signals change; cleans up itself. */
  private readonly workationsResource = rxResource({
    params: () => ({ sortBy: this.sortBy(), direction: this.direction() }),
    stream: ({ params }) =>
      this.workationService.getWorkations(params.sortBy, params.direction),
    defaultValue: [] as Workation[],
  });

  // `value()` throws while the resource is in an error state, so guard with hasValue().
  protected readonly workations = computed<Workation[]>(() =>
    this.workationsResource.hasValue() ? this.workationsResource.value() : [],
  );
  protected readonly loading = this.workationsResource.isLoading;
  protected readonly error = computed(() => this.workationsResource.error() !== undefined);

  /** Reacts to a Material sort change; empty direction resets to the default order. */
  protected onSortChange(sort: Sort): void {
    if (sort.direction) {
      this.sortBy.set(sort.active);
      this.direction.set(sort.direction === 'desc' ? SortDirection.Desc : SortDirection.Asc);
    } else {
      this.sortBy.set(DEFAULT_SORT.field);
      this.direction.set(DEFAULT_SORT.direction);
    }
  }
}
