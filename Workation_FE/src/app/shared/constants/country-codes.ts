import { getAlpha2Code, registerLocale } from 'i18n-iso-countries';
import enLocale from 'i18n-iso-countries/langs/en.json';

// Register the English locale once so country names can be resolved to ISO codes.
registerLocale(enLocale);

/**
 * Resolves a country display name to its ISO 3166-1 alpha-2 code (lowercase),
 * for use with the `flag-icons` classes — e.g. "Germany" -> "de" (`fi fi-de`).
 *
 * Backed by `i18n-iso-countries`, so any country name is supported (not a
 * hardcoded list). Returns an empty string for unknown/blank names so the
 * template can hide the flag rather than render a broken icon.
 */
export function getCountryCode(country: string | null | undefined): string {
  if (!country) {
    return '';
  }
  const code = getAlpha2Code(country.trim(), 'en');
  return code ? code.toLowerCase() : '';
}
