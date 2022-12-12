package com.skypro.employee;

import com.skypro.employee.exception.InvalidEmployeeRequestException;
import com.skypro.employee.model.Employee;
import com.skypro.employee.service.DepartmentService;
import com.skypro.employee.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    private final List <Employee> employees = List.of(
            new Employee("TestA", "SurnameA", 1,50000 ),
            new Employee("TestB", "SurnameB", 1,60000 ),
            new Employee("TestC", "SurnameC", 2,70000 ),
            new Employee("TestD", "SurnameD", 2,80000 ),
            new Employee("TestE", "SurnameE", 3,90000 ),
            new Employee("TestF", "SurnameF", 3,100000 )
    );

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setUp(){
        when(employeeService.getAllEmployees())
                .thenReturn(employees);
    }
    @Test
    void getDepartmentEmployees(){
        Collection<Employee> employeeList = this.departmentService.getDepartmentEmployees(1);
        assertThat(employeeList).hasSize(2)
                .contains(employees.get(0), employees.get(1));
    }

    @Test
    void getSumSalaryPerDep(){
        int sum = this.departmentService.getSumSalaryPerDep(1);
        assertThat(sum).isEqualTo(110000);
    }
    @Test
    void getMaxSalaryPerDep(){
        int max = this.departmentService.getMaxSalaryPerDep(3);
        assertThat(max).isEqualTo(100000);
    }
    @Test
    void getMinSalaryPerDep(){
        int min = this.departmentService.getMinSalaryPerDep(3);
        assertThat(min).isEqualTo(90000);
    }
    @Test
    void  getEmployeesGroupedPerDep(){
        Map <Integer, List<Employee>> groupedEmployees = this.departmentService.getEmployeesGroupedPerDep();
        assertThat(groupedEmployees).hasSize(3)
                .containsKey(1).containsKey(2).containsKey(3)
                .containsEntry(1,List.of(employees.get(0),employees.get(1)))
                .containsEntry(2,List.of(employees.get(2),employees.get(3)))
                .containsEntry(3,List.of(employees.get(4),employees.get(5)));
    }
    @Test
    void whenNoEmployeesThenGroupReturnEmptyMap(){
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        Map <Integer, List<Employee>> groupedEmployees = this.departmentService.getEmployeesGroupedPerDep();

        assertThat(groupedEmployees).isEmpty();
    }

    @Test
    void whenNoEmployeesThenMaxSalaryThrowsException(){
        when(employeeService.getAllEmployees()).thenReturn(List.of());
      Assertions.assertThatThrownBy(()-> departmentService.getMaxSalaryPerDep(0))
              .isInstanceOf(InvalidEmployeeRequestException.class);
    }
}
