package com.codegym.service;

import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService extends  GeneralService<Employee> {
    void save(EmployeeForm employeeForm);
    Page<Employee> findAllByNameContaining(String name, Pageable pageable);
}
