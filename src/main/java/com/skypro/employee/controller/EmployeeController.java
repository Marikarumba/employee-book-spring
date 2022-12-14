package com.skypro.employee.controller;

import com.skypro.employee.exception.InvalidEmployeeRequestException;
import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity <Employee> addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            return ResponseEntity.ok(this.employeeService.addEmployee(employeeRequest));
        } catch (InvalidEmployeeRequestException e){
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
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
