package com.skypro.employee.controller;

import com.skypro.employee.model.Employee;
import com.skypro.employee.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/department/employees")
    public Collection<Employee> getDepartmentEmployees(int department) {
        return departmentService.getDepartmentEmployees(department);
    }

    @GetMapping("/department/{department}/salary/sum")
    public int getSumSalaryPerDep ( @PathVariable int department) {
        return departmentService.getSumSalaryPerDep(department);
    }

    @GetMapping("/department/{department}/salary/max")
    public int getMaxSalaryPerDep ( @PathVariable int department) {
        return departmentService.getMaxSalaryPerDep(department);
    }
    @GetMapping("/department/{department}/salary/min")
    public int getMinSalaryPerDep ( @PathVariable int department) {
        return departmentService.getMinSalaryPerDep(department);
    }

    @GetMapping("/department/employees/group")
    public Map<Integer, List<Employee>> getEmployeesGroupedPerDep() {
        return departmentService.getEmployeesGroupedPerDep();
    }

}