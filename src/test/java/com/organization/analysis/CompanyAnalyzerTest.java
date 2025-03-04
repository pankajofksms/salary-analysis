package com.organization.analysis;

import com.organization.analysis.exceptions.MultipleCEOsException;
import com.organization.analysis.exceptions.NoCEOFoundException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanyAnalyzerTest {

    @Test
    void testFindCEO_Success() {
        List<Employee> employees = Arrays.asList(
                new Employee(123, "Pankaj", "Kumar", 60000, null),  // CEO
                new Employee(124, "Rohan", "Krishna", 45000, 123),
                new Employee(125, "Sagar", "Haider", 47000, 123)
        );

        CompanyAnalyzer analyzer = new CompanyAnalyzer(employees);
        Employee ceo = analyzer.findCEO();

        assertNotNull(ceo);
        assertEquals(123, ceo.getId());
        assertEquals("Joe", ceo.getFirstName());
    }

    @Test
    void testFindCEO_MultipleCEOs_ShouldThrowException() {
        List<Employee> employees = Arrays.asList(
                new Employee(123, "Pankaj", "Kumar", 60000, null),  // CEO 1
                new Employee(200, "Rohan", "Krishna", 70000, null), // CEO 2
                new Employee(125, "Sagar", "Haider", 47000, 123)
        );

        CompanyAnalyzer analyzer = new CompanyAnalyzer(employees);

        assertThrows(MultipleCEOsException.class, analyzer::findCEO);
    }

    @Test
    void testFindCEO_NoCEO_ShouldThrowException() {
        List<Employee> employees = Arrays.asList(
                new Employee(124, "Pankaj", "Kumar", 45000, 123),
                new Employee(125, "Rohan", "Krishna", 47000, 123)
        );

        CompanyAnalyzer analyzer = new CompanyAnalyzer(employees);

        assertThrows(NoCEOFoundException.class, analyzer::findCEO);
    }
}
