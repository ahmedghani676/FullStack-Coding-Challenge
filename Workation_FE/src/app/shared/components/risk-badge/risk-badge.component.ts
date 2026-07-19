import { ChangeDetectionStrategy, Component, computed, input } from '@angular/core';

import { RiskLevel } from '../../../core/models/workation.model';
import { RiskLabelPipe } from '../../pipes/risk-label.pipe';

/**
 * Presentational badge for a workation's risk: a colored shield SVG
 * (red HIGH, amber LOW, green NO) served from `public/icons/risk-<level>.svg`,
 * next to a slate label.
 */
@Component({
  selector: 'app-risk-badge',
  imports: [RiskLabelPipe],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './risk-badge.component.html',
  styleUrl: './risk-badge.component.scss',
})
export class RiskBadgeComponent {
  readonly risk = input.required<RiskLevel>();

  /** Lowercase level, e.g. RiskLevel.High -> "high" for `.risk--high` + icon src. */
  protected readonly riskModifier = computed(() => String(this.risk()).toLowerCase());
}
