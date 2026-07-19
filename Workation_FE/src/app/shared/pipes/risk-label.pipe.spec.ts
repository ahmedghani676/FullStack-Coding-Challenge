import { RiskLevel } from '../../core/models/workation.model';
import { RiskLabelPipe } from './risk-label.pipe';

describe('RiskLabelPipe', () => {
  const pipe = new RiskLabelPipe();

  it('labels HIGH as "High risk"', () => {
    expect(pipe.transform(RiskLevel.High)).toBe('High risk');
  });

  it('labels both LOW and NO as "No risk"', () => {
    expect(pipe.transform(RiskLevel.Low)).toBe('No risk');
    expect(pipe.transform(RiskLevel.No)).toBe('No risk');
  });

  it('treats missing values as "No risk"', () => {
    expect(pipe.transform(null)).toBe('No risk');
    expect(pipe.transform(undefined)).toBe('No risk');
  });
});
