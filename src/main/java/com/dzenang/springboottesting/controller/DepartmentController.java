package com.dzenang.springboottesting.controller;

import com.dzenang.springboottesting.entity.Department;
import com.dzenang.springboottesting.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(@Autowired DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public Department createDepartment(@Valid @RequestBody Department department) {
        return departmentService.saveDepartment(department);
    }

    @GetMapping
    public List<Department> getAllDepartments(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortingOrder
    ) {
        return departmentService.getAllDepartments(pageNo, pageSize, sortBy, sortingOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartment(id);
        if (department == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(department);
    }

    @GetMapping("/single/{name}")
    public Department getDepartmentByName(@PathVariable String name) {
        return departmentService.getDepartment(name);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        return departmentService.updateDepartment(id, department);
    }

    @DeleteMapping("{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}
