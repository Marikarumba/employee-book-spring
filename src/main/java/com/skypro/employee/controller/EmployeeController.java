package com.skypro.employee.controller;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;


@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")

    public Collection<Employee> getAllEmployees() {
        return this.employeeService.getAllEmployees();
    }


    @PostMapping("/employees")

    public Employee addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return this.employeeService.addEmployee(employeeRequest);
    }


    @PostMapping("/employees/salary/sum")
    public int sumSalary() {
        return this.employeeService.sumSalary();
    }

    @GetMapping("/employee/salary/min")
    public Employee minSalary() {
        return this.employeeService.minSalary();
    }

    @GetMapping("/employee/salary/max")
    public Employee maxSalary() {
        return this.employeeService.maxSalary();
    }

    @GetMapping("/employee/high-salary")
    public List<Employee> highSalary() {
        return this.employeeService.highSalary();
    }

}
