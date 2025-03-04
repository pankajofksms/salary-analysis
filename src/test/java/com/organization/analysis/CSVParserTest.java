package com.organization.analysis;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVParserTest {

    @Test
    void testParseCSV() throws IOException {
        String testFilePath = "src/test/resources/test_employees.csv";

        List<Employee> employees = CSVParser.parseCSV(testFilePath);

        assertNotNull(employees);
        assertFalse(employees.isEmpty());

        Employee ceo = employees.get(0);
        assertNull(ceo.getManagerId());

        Employee manager = employees.stream()
                .filter(emp -> emp.getId() == 124)
                .findFirst()
                .orElse(null);
        assertNotNull(manager);
        assertEquals(124, manager.getId());

    }
}

