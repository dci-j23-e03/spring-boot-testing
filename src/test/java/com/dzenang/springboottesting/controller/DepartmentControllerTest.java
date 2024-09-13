package com.dzenang.springboottesting.controller;

import com.dzenang.springboottesting.entity.Department;
import com.dzenang.springboottesting.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @Test
    void getDepartmentById_ShouldReturnDepartment_WhenIdExists() throws Exception {
        // given
        Long departmentId = 1L;
        Department department = new Department(departmentId, "HR", "Main building, blok A", "HR01");
        when(departmentService.getDepartment(departmentId)).thenReturn(department);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get("/departments/{id}", departmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(departmentId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("HR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Main building, blok A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("HR01"));

        verify(departmentService, times(1)).getDepartment(departmentId);
    }

    @Test
    void getDepartmentById_ShouldNotReturnDepartment_WhenIdDoesNotExist() throws Exception {
        // given
        Long nonExistentDepartmentId = 22L;
        when(departmentService.getDepartment(nonExistentDepartmentId)).thenReturn(null);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get("/departments/{id}", nonExistentDepartmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(departmentService, times(1)).getDepartment(nonExistentDepartmentId);
    }

}