import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskLevel } from '../../../core/models/workation.model';
import { RiskBadgeComponent } from './risk-badge.component';

describe('RiskBadgeComponent', () => {
  let fixture: ComponentFixture<RiskBadgeComponent>;

  beforeEach(() => {
    fixture = TestBed.createComponent(RiskBadgeComponent);
  });

  const render = (risk: RiskLevel) => {
    fixture.componentRef.setInput('risk', risk);
    fixture.detectChanges();
    return fixture.nativeElement as HTMLElement;
  };

  const iconSrc = (el: HTMLElement) => el.querySelector('img.risk__icon')?.getAttribute('src');

  it('renders HIGH as "High risk" with the red shield icon', () => {
    const el = render(RiskLevel.High);
    expect(el.querySelector('.risk')?.classList).toContain('risk--high');
    expect(el.querySelector('.risk__label')?.textContent?.trim()).toBe('High risk');
    expect(iconSrc(el)).toContain('risk-high.svg');
  });

  it('renders LOW as "No risk" with the amber shield icon', () => {
    const el = render(RiskLevel.Low);
    expect(el.querySelector('.risk')?.classList).toContain('risk--low');
    expect(el.querySelector('.risk__label')?.textContent?.trim()).toBe('No risk');
    expect(iconSrc(el)).toContain('risk-low.svg');
  });

  it('renders NO as "No risk" with the green shield icon', () => {
    const el = render(RiskLevel.No);
    expect(el.querySelector('.risk')?.classList).toContain('risk--no');
    expect(el.querySelector('.risk__label')?.textContent?.trim()).toBe('No risk');
    expect(iconSrc(el)).toContain('risk-no.svg');
  });
});
