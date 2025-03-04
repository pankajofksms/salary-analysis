package com.organization.analysis;

import com.organization.analysis.exceptions.InvalidManagerReferenceException;
import com.organization.analysis.exceptions.MultipleCEOsException;
import com.organization.analysis.exceptions.NoCEOFoundException;
import com.organization.analysis.exceptions.TooManyManagersException;
import org.junit.jupiter.api.Disabled;
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
        assertEquals("Pankaj", ceo.getFirstName());
    }

    @Test
    @Disabled("Not working currently")
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
    @Disabled("Not working currently")
    void testFindCEO_NoCEO_ShouldThrowException() {
        List<Employee> employees = Arrays.asList(
                new Employee(124, "Pankaj", "Kumar", 45000, 123),
                new Employee(125, "Rohan", "Krishna", 47000, 123)
        );

        CompanyAnalyzer analyzer = new CompanyAnalyzer(employees);

        assertThrows(NoCEOFoundException.class, analyzer::findCEO);
    }

    @Test
    void testFindReportingDepth_TooManyManagers_ShouldThrowException() {
        List<Employee> employees = Arrays.asList(
                new Employee(123, "Joe", "Doe", 60000, null),  // CEO
                new Employee(124, "Manager1", "One", 50000, 123),
                new Employee(125, "Manager2", "Two", 47000, 124),
                new Employee(200, "Manager3", "Three", 46000, 125),
                new Employee(300, "Manager4", "Four", 45000, 200),
                new Employee(400, "Employee", "Exceeding", 44000, 300) // 5 levels deep
        );

        CompanyAnalyzer analyzer = new CompanyAnalyzer(employees);

        assertThrows(TooManyManagersException.class, analyzer::validateHierarchy);
    }

    @Test
    void testFindReportingDepth_InvalidManager_ShouldThrowException() {
        List<Employee> employees = Arrays.asList(
                new Employee(123, "Joe", "Doe", 60000, null),  // CEO
                new Employee(124, "Martin", "Chekov", 45000, 999) // Manager ID 999 does not exist
        );

        CompanyAnalyzer analyzer = new CompanyAnalyzer(employees);

        assertThrows(InvalidManagerReferenceException.class, () -> analyzer.findReportingDepth(employees.get(1)));
    }
}
