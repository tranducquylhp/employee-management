package com.codegym.service;

import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;

public interface EmployeeService extends  GeneralService<Employee> {
    void save(EmployeeForm employeeForm);
}
