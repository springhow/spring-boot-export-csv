package com.example.springboot.exportcsv.services;

import com.example.springboot.exportcsv.jpa.Employee;
import com.example.springboot.exportcsv.jpa.EmployeeRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class CsvExportService {

    private static final Logger log = getLogger(CsvExportService.class);

    private final EmployeeRepository employeeRepository;

    public CsvExportService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void writeEmployeesToCsv(Writer writer) {

        List<Employee> employees = employeeRepository.findAll();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (Employee employee : employees) {
                csvPrinter.printRecord(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getDepartment());
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }
}
