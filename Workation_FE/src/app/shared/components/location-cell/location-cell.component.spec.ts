import { TestBed } from '@angular/core/testing';

import { LocationCellComponent } from './location-cell.component';

describe('LocationCellComponent', () => {
  it('renders the country name and its flag-icons class', () => {
    const fixture = TestBed.createComponent(LocationCellComponent);
    fixture.componentRef.setInput('country', 'Germany');
    fixture.detectChanges();

    const el = fixture.nativeElement as HTMLElement;
    expect(el.textContent?.trim()).toBe('Germany');
    expect(el.querySelector('.fi')?.classList).toContain('fi-de');
  });

  it('renders no flag class for an unknown country', () => {
    const fixture = TestBed.createComponent(LocationCellComponent);
    fixture.componentRef.setInput('country', 'Atlantis');
    fixture.detectChanges();

    const flag = (fixture.nativeElement as HTMLElement).querySelector('.fi');
    expect(flag?.className).toBe('fi fi-');
  });
});
