package com.workflex.workation.csv;

import com.workflex.workation.domain.RiskLevel;
import com.workflex.workation.domain.Workation;
import com.workflex.workation.repository.WorkationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkationCsvImporterTest {

    @Mock
    private WorkationRepository workationRepository;

    @Captor
    private ArgumentCaptor<List<Workation>> savedWorkationsCaptor;

    private final WorkationCsvParser csvParser = new WorkationCsvParser();
    private final WorkationCsvMapper csvMapper = new WorkationCsvMapper();

    @Test
    void importsWorkationsWhenTableIsEmpty() {
        when(workationRepository.count()).thenReturn(0L);
        String csv = """
                workationId,employee,origin,destination,start,end,workingDays,risk
                w1,Steffen Jacobs,Germany,United States,2024-01-02,2024-12-31,65,HIGH
                """;
        ByteArrayResource resource = new ByteArrayResource(csv.getBytes(StandardCharsets.UTF_8));

        WorkationCsvImporter importer = new WorkationCsvImporter(workationRepository, csvParser, csvMapper, resource);
        importer.run(null);

        verify(workationRepository, times(1)).saveAll(anyList());
    }

    @Test
    void skipsImportWhenWorkationsAlreadyExist() {
        when(workationRepository.count()).thenReturn(5L);
        ByteArrayResource resource = new ByteArrayResource(new byte[0]);

        WorkationCsvImporter importer = new WorkationCsvImporter(workationRepository, csvParser, csvMapper, resource);
        importer.run(null);

        verify(workationRepository, never()).saveAll(anyList());
    }

    @Test
    void savesParsedWorkationsWithCorrectData() {
        when(workationRepository.count()).thenReturn(0L);
        String csv = """
                workationId,employee,origin,destination,start,end,workingDays,risk
                w5,Ayushi Singh,Germany,India,2023-03-13,2023-04-30,35,NO
                """;
        ByteArrayResource resource = new ByteArrayResource(csv.getBytes(StandardCharsets.UTF_8));

        WorkationCsvImporter importer = new WorkationCsvImporter(workationRepository, csvParser, csvMapper, resource);
        importer.run(null);

        verify(workationRepository).saveAll(savedWorkationsCaptor.capture());
        assertThat(savedWorkationsCaptor.getValue())
                .extracting(Workation::getWorkationId, Workation::getEmployee, Workation::getOrigin,
                        Workation::getDestination, Workation::getStartDate, Workation::getEndDate,
                        Workation::getWorkingDays, Workation::getRisk)
                .containsExactly(tuple("w5", "Ayushi Singh", "Germany", "India",
                        LocalDate.of(2023, 3, 13), LocalDate.of(2023, 4, 30), 35, RiskLevel.NO));
    }
}
