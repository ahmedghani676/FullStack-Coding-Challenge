package com.workflex.workation.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.workflex.workation.constant.CsvImportMessages;
import com.workflex.workation.constant.WorkationCsvColumns;
import com.workflex.workation.domain.RiskLevel;
import com.workflex.workation.exception.CsvImportException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses the workations CSV into {@link WorkationCsvRecord}s. Kept free of both
 * Spring's application context / I/O wiring and the JPA entity, so the parsing
 * logic can be unit tested directly against a {@link Reader}.
 */
@Component
public class WorkationCsvParser {

    private static final int COLUMN_COUNT = WorkationCsvColumns.HEADER.size();

    public List<WorkationCsvRecord> parse(Reader reader) {
        try (CSVReader csvReader = new CSVReader(reader)) {
            List<WorkationCsvRecord> records = new ArrayList<>();

            String[] header = csvReader.readNext();
            validateHeader(header);

            String[] row;
            int lineNumber = 1;
            while ((row = csvReader.readNext()) != null) {
                lineNumber++;
                records.add(toRecord(row, lineNumber));
            }
            return records;
        } catch (IOException | CsvValidationException e) {
            throw new CsvImportException(CsvImportMessages.READ_FAILED, e);
        }
    }

    private void validateHeader(String[] header) {
        if (header == null || header.length < COLUMN_COUNT) {
            throw new CsvImportException(CsvImportMessages.INVALID_HEADER);
        }
        for (int i = 0; i < COLUMN_COUNT; i++) {
            String expected = WorkationCsvColumns.HEADER.get(i);
            if (!expected.equalsIgnoreCase(header[i].trim())) {
                throw new CsvImportException(
                        CsvImportMessages.UNEXPECTED_COLUMN.formatted(i, expected, header[i]));
            }
        }
    }

    private WorkationCsvRecord toRecord(String[] row, int lineNumber) {
        if (row.length < COLUMN_COUNT) {
            throw new CsvImportException(
                    CsvImportMessages.MALFORMED_ROW.formatted(lineNumber, COLUMN_COUNT, row.length));
        }
        try {
            return new WorkationCsvRecord(
                    row[0].trim(),
                    row[1].trim(),
                    row[2].trim(),
                    row[3].trim(),
                    LocalDate.parse(row[4].trim()),
                    LocalDate.parse(row[5].trim()),
                    Integer.parseInt(row[6].trim()),
                    RiskLevel.valueOf(row[7].trim().toUpperCase())
            );
        } catch (RuntimeException e) {
            throw new CsvImportException(
                    CsvImportMessages.ROW_PARSE_FAILED.formatted(lineNumber, String.join(",", row)), e);
        }
    }
}
