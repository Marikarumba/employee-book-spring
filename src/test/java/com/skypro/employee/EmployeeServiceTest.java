package com.skypro.employee;

import com.skypro.employee.exception.InvalidEmployeeRequestException;
import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    public void setUp(){
        this.employeeService = new EmployeeService();
        Stream.of(
              new EmployeeRequest("TestA", "SurnameA", 1,50000 ),
                new EmployeeRequest("TestB", "SurnameB", 1,60000 ),
                new EmployeeRequest("TestC", "SurnameC", 2,70000 ),
                new EmployeeRequest("TestD", "SurnameD", 2,80000 ),
                new EmployeeRequest("TestE", "SurnameE", 3,90000 ),
                new EmployeeRequest("TestF", "SurnameF", 3,100000 )
        ).forEach(e->employeeService.addEmployee(e));
    }
    @Test
    public void addEmployee(){
        EmployeeRequest request = new EmployeeRequest(
                "Valid", "Valid", 3, 50000);
        Employee result  = employeeService.addEmployee(request);
        assertEquals(request.getFirstName(),result.getFirstName());
        assertEquals(request.getLastName(),result.getLastName());
        assertEquals(request.getDepartment(),result.getDepartment());
        assertEquals(request.getSalary(),result.getSalary());

        Assertions.assertThat(employeeService.getAllEmployees()).contains(result);
    }

    @Test
    public void listEmployees(){

        Collection<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).hasSize(6);
        Assertions.assertThat(employees)
                .first()
                .extracting(Employee::getFirstName)
                .isIn("TestA");
    }

    @Test
    public void sumSalary(){
        int sum = employeeService.sumSalary();
        Assertions.assertThat(sum).isEqualTo(450000);
    }

    @Test
    public void minSalary() {
        Employee employee = employeeService.minSalary();
        Assertions.assertThat(employee).isNotNull().extracting(Employee::getFirstName)
                .isEqualTo("TestA");
    }

    @Test
    public void maxSalary() {
        Employee employee = employeeService.maxSalary();
        Assertions.assertThat(employee).isNotNull().extracting(Employee::getFirstName)
                .isEqualTo("TestF");
    }

    @Test
    public void deleteEmployee(){
        employeeService.deleteEmployee(0);
        Collection<Employee>employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).hasSize(5);
    }

    @Test
    public void errAddEmployee(){
        EmployeeRequest request = new EmployeeRequest(
                "noValid2", "noValid2", 3, 50000);
        Assertions.assertThatThrownBy(()->  employeeService.addEmployee(request))
                .isInstanceOf(InvalidEmployeeRequestException.class);
    }

    @Test
    public void capitalizeIsOk(){
        EmployeeRequest request = new EmployeeRequest(
                "valid", "valid", 3, 50000);
        Employee result = employeeService.addEmployee(request);
        Assertions.assertThat(result.getFirstName())
                .isEqualTo("Valid");
    }

    @Test
    void  averageSalary() {
        List<Employee> employees = this.employeeService.highSalary();
        assertThat(employees).hasSize(3);
        assertThat(employees.stream().map(Employee::getSalary).allMatch(e -> e > 75000)).isTrue();
    }
}
