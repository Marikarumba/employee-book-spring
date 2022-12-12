package com.skypro.employee.service;

import com.skypro.employee.exception.InvalidEmployeeRequestException;
import com.skypro.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DepartmentService {

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private final EmployeeService employeeService;

    private Stream<Employee> getEmployeesByDepStream(int department){
        return employeeService.getAllEmployees().stream()
                .filter(e ->e.getDepartment() ==department);
    }

    public Collection<Employee> getDepartmentEmployees (int department){
        return getEmployeesByDepStream(department)
                .collect(Collectors.toList());
    }

    public int getSumSalaryPerDep(int department){
        return getEmployeesByDepStream(department)
                .mapToInt(Employee::getSalary).sum();
    }

    public int getMaxSalaryPerDep(int department){
        return getEmployeesByDepStream(department)
                .mapToInt(Employee::getSalary).max()
                .orElseThrow(InvalidEmployeeRequestException::new);
    }
    public int getMinSalaryPerDep(int department){
        return getEmployeesByDepStream(department)
                .mapToInt(Employee::getSalary).min()
                .orElseThrow(InvalidEmployeeRequestException::new);
    }

    public Map <Integer, List<Employee>> getEmployeesGroupedPerDep(){
        return employeeService.getAllEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

}
