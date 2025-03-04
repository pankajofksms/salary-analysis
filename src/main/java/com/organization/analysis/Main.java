package com.organization.analysis;

import com.organization.analysis.exceptions.MultipleCEOsException;
import com.organization.analysis.exceptions.NoCEOFoundException;
import com.organization.analysis.exceptions.SalaryViolationException;
import com.organization.analysis.exceptions.TooManyManagersException;

import java.io.IOException;
import java.util.List;

/**
 * Main class to execute the Company Salary and Hierarchy Analysis.
 */
public class Main {
    public static void main(String[] args) {

        String filePath = "src/main/resources/employees.csv";

        try {
            List<Employee> employees = CSVParser.parseCSV(filePath);

            CompanyAnalyzer companyAnalyzer = new CompanyAnalyzer(employees);

            try {
                Employee ceo = companyAnalyzer.findCEO();
                System.out.println("CEO Identified: " + ceo.getFirstName() + " " + ceo.getLastName());
            } catch (MultipleCEOsException | NoCEOFoundException e) {
                System.err.println("Error: " + e.getMessage());
                return;
            }

            try {
                String hierarchyReport = companyAnalyzer.validateHierarchy();
                System.out.println("\nHierarchy Validation:\n" + hierarchyReport);
            } catch (TooManyManagersException e) {
                System.err.println("Error: " + e.getMessage());
            }

            try {
                SalaryValidator salaryValidator = new SalaryValidator(employees);
                String salaryReport = salaryValidator.validateSalaries();
                System.out.println("\nSalary Validation:\n" + salaryReport);
            } catch (SalaryViolationException e) {
                System.err.println("Error: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }
}
