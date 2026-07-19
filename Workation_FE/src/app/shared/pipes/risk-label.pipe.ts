import { Pipe, PipeTransform } from '@angular/core';

import { RiskLevel } from '../../core/models/workation.model';

/**
 * Maps a {@link RiskLevel} to its user-facing label. Both NO and LOW are shown
 * as "No risk" (the template colors them differently); HIGH is "High risk".
 */
@Pipe({ name: 'riskLabel' })
export class RiskLabelPipe implements PipeTransform {
  transform(risk: RiskLevel | null | undefined): string {
    return risk === RiskLevel.High ? 'High risk' : 'No risk';
  }
}
