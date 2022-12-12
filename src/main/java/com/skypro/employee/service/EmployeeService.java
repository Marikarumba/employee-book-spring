package com.skypro.employee.service;

import com.skypro.employee.exception.InvalidEmployeeRequestException;
import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final Map < Integer, Employee > employees = new HashMap<>();

    public Collection<Employee > getAllEmployees () {
        return this.employees.values();
    }


    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (!StringUtils.isAlpha(employeeRequest.getFirstName()) ||
        !StringUtils.isAlpha(employeeRequest.getLastName())) {
            throw new InvalidEmployeeRequestException();
        }
        Employee employee = new Employee(
                StringUtils.capitalize(employeeRequest.getFirstName()),
                StringUtils.capitalize(employeeRequest.getLastName()),
                employeeRequest.getDepartment(),
                employeeRequest.getSalary());

        this.employees.put(employee.getId(), employee);
        return employee;
    }

    public int sumSalary (){
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }
    public Employee minSalary (){
        return employees.values().stream()
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElse(null);
    }

    public Employee maxSalary (){
        return employees.values().stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElse(null);
    }

    public List<Employee > highSalary () {
       Double average = averageSalary();
       if (averageSalary()==null){
           return Collections.emptyList();
       }
        return this.employees.values().stream()
                .filter(e -> e.getSalary()>average)
                .collect( Collectors.toList() );
    }
    private Double averageSalary() {
        return employees.values()
                .stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));

    }

    public Employee deleteEmployee(int id){
        return employees.remove(id);
    }


}
