import { CountryCodePipe } from './country-code.pipe';

describe('CountryCodePipe', () => {
  const pipe = new CountryCodePipe();

  it('maps known country names to ISO alpha-2 codes', () => {
    expect(pipe.transform('Germany')).toBe('de');
    expect(pipe.transform('United States')).toBe('us');
    expect(pipe.transform('India')).toBe('in');
  });

  it('trims surrounding whitespace before lookup', () => {
    expect(pipe.transform('  Spain  ')).toBe('es');
  });

  it('returns an empty string for unknown or missing countries', () => {
    expect(pipe.transform('Atlantis')).toBe('');
    expect(pipe.transform(null)).toBe('');
    expect(pipe.transform(undefined)).toBe('');
  });
});
