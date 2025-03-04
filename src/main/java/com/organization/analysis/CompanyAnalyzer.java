package com.organization.analysis;

import com.organization.analysis.exceptions.MultipleCEOsException;
import com.organization.analysis.exceptions.NoCEOFoundException;
import com.organization.analysis.exceptions.TooManyManagersException;
import com.organization.analysis.exceptions.InvalidManagerReferenceException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyAnalyzer {

    private final List<Employee> employees;
    private final Map<Integer, Employee> employeeMap;
    private final Employee ceo;
    private static final int MAX_HIERARCHY_DEPTH = 4;

    public CompanyAnalyzer(List<Employee> employees) {
        this.employees = employees;
        this.employeeMap = new HashMap<>();

        for (Employee emp : employees) {
            employeeMap.put(emp.getId(), emp);
        }

        this.ceo = findCEO();
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

    public int findReportingDepth(Employee employee) {
        int depth = 0;
        Integer managerId = employee.getManagerId();

        while (managerId != null) {
            Employee manager = employeeMap.get(managerId);
            if (manager == null) {
                throw new InvalidManagerReferenceException("Invalid hierarchy: Manager with ID " + managerId + " not found.");
            }
            depth++;
            managerId = manager.getManagerId();
        }

        return depth;
    }

    public String validateHierarchy() {
        StringBuilder report = new StringBuilder("Employees exceeding hierarchy depth limit:\n");

        for (Employee emp : employees) {
            int depth = findReportingDepth(emp);
            if (depth > MAX_HIERARCHY_DEPTH) {
                report.append(emp.getFirstName()).append(" ").append(emp.getLastName())
                        .append(" (ID: ").append(emp.getId())
                        .append(") exceeds depth limit by ").append(depth - MAX_HIERARCHY_DEPTH).append(" levels.\n");

                // Throwing exception when a violation is found
                throw new TooManyManagersException("Employee " + emp.getFirstName() + " " + emp.getLastName() +
                        " exceeds the reporting depth limit of " + MAX_HIERARCHY_DEPTH + ".");
            }
        }

        return report.toString();
    }
}
