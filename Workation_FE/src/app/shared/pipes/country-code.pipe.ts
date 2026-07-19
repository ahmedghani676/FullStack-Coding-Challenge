import { Pipe, PipeTransform } from '@angular/core';

import { getCountryCode } from '../constants/country-codes';

/**
 * Resolves a country display name to its ISO 3166-1 alpha-2 code for use with
 * the `flag-icons` classes, e.g. "Germany" -> "de" (rendered as `fi fi-de`).
 * Returns an empty string for unknown countries so the template can hide the
 * flag rather than render a broken icon.
 */
@Pipe({ name: 'countryCode' })
export class CountryCodePipe implements PipeTransform {
  transform(country: string | null | undefined): string {
    return getCountryCode(country);
  }
}
