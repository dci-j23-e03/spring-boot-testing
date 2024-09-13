package com.dzenang.springboottesting.service;

import com.dzenang.springboottesting.entity.Department;
import com.dzenang.springboottesting.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveDepartment_ShouldReturnSavedDepartment() {
        // given
        Department department = new Department(1L, "HR", "Main building, blok A", "HR01");
        Mockito.when(departmentRepository.save(department)).thenReturn(department);

        // when
        Department savedDepartment = departmentService.saveDepartment(department);

        // then
        assertNotNull(savedDepartment);
        assertEquals(department.getId(), savedDepartment.getId());
        assertEquals(department.getName(), savedDepartment.getName());
        assertEquals(department.getAddress(), savedDepartment.getAddress());
        assertEquals(department, savedDepartment);
        Mockito.verify(departmentRepository, Mockito.times(1)).save(department);
    }
}