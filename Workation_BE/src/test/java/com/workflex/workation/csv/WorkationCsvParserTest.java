package com.workflex.workation.csv;

import com.workflex.workation.domain.RiskLevel;
import com.workflex.workation.exception.CsvImportException;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WorkationCsvParserTest {

    private final WorkationCsvParser parser = new WorkationCsvParser();

    @Test
    void parsesAllRowsIntoRecords() {
        String csv = """
                workationId,employee,origin,destination,start,end,workingDays,risk
                w1,Steffen Jacobs,Germany,United States,2024-01-02,2024-12-31,65,HIGH
                w4,Andre Fischer,Germany,Greece,2023-05-22,2023-06-30,50,LOW
                w5,Ayushi Singh,Germany,India,2023-03-13,2023-04-30,35,NO
                """;

        List<WorkationCsvRecord> records = parser.parse(new StringReader(csv));

        assertThat(records).hasSize(3);
        WorkationCsvRecord first = records.get(0);
        assertThat(first.workationId()).isEqualTo("w1");
        assertThat(first.employee()).isEqualTo("Steffen Jacobs");
        assertThat(first.origin()).isEqualTo("Germany");
        assertThat(first.destination()).isEqualTo("United States");
        assertThat(first.startDate()).isEqualTo(LocalDate.of(2024, 1, 2));
        assertThat(first.endDate()).isEqualTo(LocalDate.of(2024, 12, 31));
        assertThat(first.workingDays()).isEqualTo(65);
        assertThat(first.risk()).isEqualTo(RiskLevel.HIGH);
    }

    @Test
    void distinguishesLowRiskFromNoRisk() {
        String csv = """
                workationId,employee,origin,destination,start,end,workingDays,risk
                w4,Andre Fischer,Germany,Greece,2023-05-22,2023-06-30,50,LOW
                w5,Ayushi Singh,Germany,India,2023-03-13,2023-04-30,35,NO
                """;

        List<WorkationCsvRecord> records = parser.parse(new StringReader(csv));

        assertThat(records.get(0).risk()).isEqualTo(RiskLevel.LOW);
        assertThat(records.get(1).risk()).isEqualTo(RiskLevel.NO);
    }

    @Test
    void throwsOnUnexpectedHeader() {
        String csv = """
                id,name,from,to,start,end,days,risk
                w1,Steffen Jacobs,Germany,United States,2024-01-02,2024-12-31,65,HIGH
                """;

        assertThatThrownBy(() -> parser.parse(new StringReader(csv)))
                .isInstanceOf(CsvImportException.class)
                .hasMessageContaining("workationId");
    }

    @Test
    void throwsOnMalformedRow() {
        String csv = """
                workationId,employee,origin,destination,start,end,workingDays,risk
                w1,Steffen Jacobs,Germany,United States,2024-01-02,2024-12-31,65,NOT_A_RISK
                """;

        assertThatThrownBy(() -> parser.parse(new StringReader(csv)))
                .isInstanceOf(CsvImportException.class)
                .hasMessageContaining("line 2");
    }
}
