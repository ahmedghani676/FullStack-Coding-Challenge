package com.workflex.workation.csv;

import com.workflex.workation.domain.RiskLevel;
import com.workflex.workation.domain.Workation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class WorkationCsvMapperTest {

    private final WorkationCsvMapper mapper = new WorkationCsvMapper();

    @Test
    void mapsEveryFieldFromRecordToEntity() {
        WorkationCsvRecord record = new WorkationCsvRecord(
                "w5", "Ayushi Singh", "Germany", "India",
                LocalDate.of(2023, 3, 13), LocalDate.of(2023, 4, 30), 35, RiskLevel.NO);

        Workation entity = mapper.toEntity(record);

        assertThat(entity.getWorkationId()).isEqualTo("w5");
        assertThat(entity.getEmployee()).isEqualTo("Ayushi Singh");
        assertThat(entity.getOrigin()).isEqualTo("Germany");
        assertThat(entity.getDestination()).isEqualTo("India");
        assertThat(entity.getStartDate()).isEqualTo(LocalDate.of(2023, 3, 13));
        assertThat(entity.getEndDate()).isEqualTo(LocalDate.of(2023, 4, 30));
        assertThat(entity.getWorkingDays()).isEqualTo(35);
        assertThat(entity.getRisk()).isEqualTo(RiskLevel.NO);
    }
}
