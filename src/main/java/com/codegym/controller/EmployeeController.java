package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;
import com.codegym.service.DepartmentService;
import com.codegym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @ModelAttribute("departments")
    public Iterable<Department> provinces(){
        return departmentService.findAll();
    }

    @GetMapping("/employees")
    public ModelAndView listEmployee(@RequestParam("s") Optional<String> s, @PageableDefault(value = 10)  Pageable pageable){
        Page<Employee> employees;
        if(s.isPresent()){
            employees = employeeService.findAllByNameContaining(s.get(), pageable);
        } else {
            employees = employeeService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }

    @GetMapping("/employee/create")
    public ModelAndView createEmployee(){
        ModelAndView modelAndView = new ModelAndView("/employee/create","employeeForm",new EmployeeForm());
        return modelAndView;
    }

    @PostMapping("/employee/create")
    public String saveEmployee(@Validated @ModelAttribute EmployeeForm employeeForm, BindingResult result){
        if (result.hasFieldErrors()) {
            return "/employee/create";
        }
        employeeService.save(employeeForm);
        return "redirect:/employees";
    }

    @GetMapping("/employee/edit/{id}")
    public ModelAndView showEditForm(@PathVariable long id){
        Employee employee = employeeService.findById(id);
        if(employee != null) {
            ModelAndView modelAndView = new ModelAndView("/employee/edit");
            modelAndView.addObject("employee", employee);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/employee/edit/")
    public String updateEmployee(@Validated @ModelAttribute EmployeeForm employeeForm, BindingResult result){
        if (!result.hasFieldErrors()) {
            employeeService.save(employeeForm);
        }
        return "redirect:/employees";
    }

    @GetMapping("/employee/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable long id){
        Employee employee = employeeService.findById(id);
        if(employee != null) {
            ModelAndView modelAndView = new ModelAndView("/employee/delete");
            modelAndView.addObject("employee", employee);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/employee/delete")
    public String deleteEmployee(@ModelAttribute EmployeeForm employeeForm){
        employeeService.delete(employeeForm.getId());
        return "redirect:/employees";
    }

    @GetMapping("/employee/view/{id}")
    public ModelAndView showViewForm(@PathVariable long id){
        Employee employee = employeeService.findById(id);
        if(employee != null) {
            ModelAndView modelAndView = new ModelAndView("/employee/view");
            modelAndView.addObject("employee", employee);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

}
