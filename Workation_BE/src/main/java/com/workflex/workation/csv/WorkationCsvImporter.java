package com.workflex.workation.csv;

import com.workflex.workation.constant.CsvImportMessages;
import com.workflex.workation.domain.Workation;
import com.workflex.workation.exception.CsvImportException;
import com.workflex.workation.repository.WorkationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Imports workations from the seed CSV into the database on application startup.
 * Skips the import if the table is already populated, so restarts against a
 * persistent database don't duplicate rows.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WorkationCsvImporter implements ApplicationRunner {

    private final WorkationRepository workationRepository;
    private final WorkationCsvParser csvParser;
    private final WorkationCsvMapper csvMapper;
    private final Resource workationsCsvResource;

    @Override
    public void run(ApplicationArguments args) {
        if (workationRepository.count() > 0) {
            log.info("Workations already present in the database, skipping CSV import");
            return;
        }

        try (Reader reader = new InputStreamReader(workationsCsvResource.getInputStream(), StandardCharsets.UTF_8)) {
            List<Workation> workations = csvParser.parse(reader).stream()
                    .map(csvMapper::toEntity)
                    .toList();
            workationRepository.saveAll(workations);
            log.info("Imported {} workations from {}", workations.size(), workationsCsvResource.getFilename());
        } catch (IOException e) {
            throw new CsvImportException(CsvImportMessages.FILE_READ_FAILED, e);
        }
    }
}
