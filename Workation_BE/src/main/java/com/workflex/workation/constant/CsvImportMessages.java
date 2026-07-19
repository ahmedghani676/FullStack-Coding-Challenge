package com.workflex.workation.constant;

/**
 * Messages for {@link com.workflex.workation.exception.CsvImportException} raised
 * during the CSV import. Grouped here (separate from {@link ApiMessages}, which
 * covers the API response envelope) so all import failure wording lives in one
 * place and reads consistently.
 */
public final class CsvImportMessages {

    public static final String READ_FAILED = "Failed to read workations CSV";
    public static final String FILE_READ_FAILED = "Could not read workations CSV file";
    public static final String INVALID_HEADER = "Workations CSV is missing or has an invalid header row";

    /** Args: columnIndex, expectedColumn, actualColumn. */
    public static final String UNEXPECTED_COLUMN = "Unexpected CSV column at index %d: expected '%s' but found '%s'";

    /** Args: lineNumber, expectedColumnCount, actualColumnCount. */
    public static final String MALFORMED_ROW = "Malformed CSV row at line %d: expected %d columns but found %d";

    /** Args: lineNumber, rawRow. */
    public static final String ROW_PARSE_FAILED = "Failed to parse CSV row at line %d: %s";

    private CsvImportMessages() {
    }
}
