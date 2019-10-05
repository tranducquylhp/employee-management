package com.codegym.service;

import com.codegym.model.Department;
import com.codegym.model.Employee;

public interface DepartmentService extends GeneralService<Department>{
    void save(Department department);
    Iterable<Employee> findAllByDepartment(Department department);
}
