package com.organization.analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    public static List<Employee> parseCSV(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<Employee>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] values = line.split(",");

                if (values.length < 4) {
                    System.err.println("Invalid line: " + line);
                    continue;
                }

                int id = Integer.parseInt(values[0].trim());
                String firstName = values[1].trim();
                String lastName = values[2].trim();
                double salary = Double.parseDouble(values[3].trim());
                Integer managerId = values.length > 4 && !values[4].trim().isEmpty()
                        ? Integer.parseInt(values[4].trim())
                        : null;

                employees.add(new Employee(id, firstName, lastName, salary, managerId));
            }
        }

        return employees;
    }
}
