package com.codegym.service.impl;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.repository.DepartmentRepository;
import com.codegym.repository.EmployeeRepository;
import com.codegym.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void save(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public Iterable<Employee> findAllByDepartment(Department department) {
        List<Employee> employees = (List<Employee>) employeeRepository.findAllByDepartment(department);
        sortListEmployees(employees);
        return employees;
    }

    private void sortListEmployees(List<Employee> employees) {
        for (int i=0; i<employees.size()-1; i++){
            Employee employee1 = employees.get(i);
            for (int j=i+1; j<employees.size(); j++){
                Employee employee2 = employees.get(j);
                if (employee1.getSalary()<employee2.getSalary()){
                    Employee tempEmployee = new Employee();
                    tempEmployee.setEmployee(employee1);
                    employees.get(i).setEmployee(employee2);
                    employees.get(j).setEmployee(tempEmployee);
                }
            }
        }
    }

    @Override
    public Page<Department> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    @Override
    public void delete(long id) {
        Department department = findById(id);
        List<Employee> employees = (List<Employee>) findAllByDepartment(department);
        for(int i=0; i<employees.size(); i++){
            employeeRepository.delete(employees.get(i));
        }
        departmentRepository.delete(id);
    }

    @Override
    public Department findById(long id) {
        return departmentRepository.findOne(id);
    }

    @Override
    public Iterable findAll() {
        return departmentRepository.findAll();
    }
}
