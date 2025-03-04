package com.organization.analysis;

import com.organization.analysis.exceptions.MultipleCEOsException;
import com.organization.analysis.exceptions.NoCEOFoundException;

import java.util.List;


public class CompanyAnalyzer {

    private final List<Employee> employees;

    public CompanyAnalyzer(List<Employee> employees) {
        this.employees = employees;
    }

    public Employee findCEO() {
        Employee ceo = null;

        for (Employee emp : employees) {
            if (emp.getManagerId() == null) {
                if (ceo != null) {
                    throw new MultipleCEOsException("Multiple CEOs found in the data.");
                }
                ceo = emp;
            }
        }

        if (ceo == null) {
            throw new NoCEOFoundException("No CEO found in the data.");
        }

        return ceo;
    }
}
