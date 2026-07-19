import { ChangeDetectionStrategy, Component, input } from '@angular/core';

import { CountryCodePipe } from '../../pipes/country-code.pipe';

/**
 * Presentational cell showing a country's flag next to its name. Reused for both
 * the origin and destination columns.
 */
@Component({
  selector: 'app-location-cell',
  imports: [CountryCodePipe],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './location-cell.component.html',
  styleUrl: './location-cell.component.scss',
})
export class LocationCellComponent {
  readonly country = input.required<string>();
}
