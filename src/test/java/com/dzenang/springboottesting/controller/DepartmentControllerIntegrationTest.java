package com.dzenang.springboottesting.controller;

import com.dzenang.springboottesting.entity.Department;
import com.dzenang.springboottesting.repository.DepartmentRepository;
import com.dzenang.springboottesting.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
class DepartmentControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        departmentRepository.deleteAll();
        Department department1 = new Department(1L, "HR", "Main building, blok A", "HR01");
        Department department2 = new Department(2L, "Finance", "Main building, blok B", "Finance01");
        Department department3 = new Department(3L, "R&D", "Main building, blok C", "R&D01");
        departmentRepository.saveAll(List.of(department1, department2, department3));
    }

    @Test
    void getAllDepartments_ShouldReturnListOfDeparments() throws Exception {
        // given
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/departments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("page", "1")
                    .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value("HR"))
                .andExpect(jsonPath("$[1].name").value("Finance"))
                .andExpect(jsonPath("$[2].name").value("R&D"));
    }

}

















