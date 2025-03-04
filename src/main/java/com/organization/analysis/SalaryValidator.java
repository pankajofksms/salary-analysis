package com.organization.analysis;

import com.organization.analysis.exceptions.SalaryViolationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalaryValidator {

    private static final double MIN_SALARY_MULTIPLIER = 1.2; // at least 20% more
    private static final double MAX_SALARY_MULTIPLIER = 1.5; // more than 50% more

    private final List<Employee> employees;
    private final Map<Integer, Employee> employeeMap;

    public SalaryValidator(List<Employee> employees) {
        this.employees = employees;
        this.employeeMap = employees.stream()
                .collect(Collectors.toMap(Employee::getId, emp -> emp));
    }

    public String validateSalaries() {
        StringBuilder report = new StringBuilder("Salary validation report:\n");

        Map<Integer, List<Employee>> subordinatesMap = new HashMap<>();
        for (Employee emp : employees) {
            if (emp.getManagerId() != null) {
                subordinatesMap.computeIfAbsent(emp.getManagerId(), k -> new ArrayList<>()).add(emp);
            }
        }

        for (Map.Entry<Integer, List<Employee>> entry : subordinatesMap.entrySet()) {
            Integer managerId = entry.getKey();
            List<Employee> subordinates = entry.getValue();

            if (!employeeMap.containsKey(managerId)) {
                throw new IllegalStateException("Manager with ID " + managerId + " not found.");
            }

            Employee manager = employeeMap.get(managerId);
            double averageSalary = subordinates.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
            double minAllowedSalary = averageSalary * MIN_SALARY_MULTIPLIER;
            double maxAllowedSalary = averageSalary * MAX_SALARY_MULTIPLIER;

            if (manager.getSalary() < minAllowedSalary) {
                double deficit = minAllowedSalary - manager.getSalary();
                report.append(manager.getFirstName()).append(" ").append(manager.getLastName())
                        .append(" (ID: ").append(manager.getId())
                        .append(") earns ₹").append(String.format("%.2f", manager.getSalary()))
                        .append(", which is ₹").append(String.format("%.2f", deficit))
                        .append(" less than the minimum allowed salary of ₹")
                        .append(String.format("%.2f", minAllowedSalary)).append("\n");

                throw new SalaryViolationException(manager.getFirstName() + " " + manager.getLastName() +
                        " earns less than required. Deficit: ₹" + String.format("%.2f", deficit));
            }

            if (manager.getSalary() > maxAllowedSalary) {
                double excess = manager.getSalary() - maxAllowedSalary;
                report.append(manager.getFirstName()).append(" ").append(manager.getLastName())
                        .append(" (ID: ").append(manager.getId())
                        .append(") earns ₹").append(String.format("%.2f", manager.getSalary()))
                        .append(", which is ₹").append(String.format("%.2f", excess))
                        .append(" more than the maximum allowed salary of ₹")
                        .append(String.format("%.2f", maxAllowedSalary)).append("\n");

                throw new SalaryViolationException(manager.getFirstName() + " " + manager.getLastName() +
                        " earns more than allowed. Excess: ₹" + String.format("%.2f", excess));
            }
        }

        return report.toString();
    }
}
