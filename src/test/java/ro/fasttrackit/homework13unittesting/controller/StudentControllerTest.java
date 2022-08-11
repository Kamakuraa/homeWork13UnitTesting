package ro.fasttrackit.homework13unittesting.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.hamcrest.CoreMatchers;
import ro.fasttrackit.homework13unittesting.HomeWork13UnitTestingApplication;
import ro.fasttrackit.homework13unittesting.model.StudentFilter;
import ro.fasttrackit.homework13unittesting.model.entity.StudentEntity;
import ro.fasttrackit.homework13unittesting.model.mapper.StudentMapper;
import ro.fasttrackit.homework13unittesting.service.StudentService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@ContextConfiguration(classes = {HomeWork13UnitTestingApplication.class, StudentControllerTest.TestBeans.class})
class StudentControllerTest {
  @Autowired
  private MockMvc mvc;
  @Autowired
  private StudentService studentService;

  @Autowired
  private StudentMapper mapper;

  @Test
  @DisplayName("GET /students")
  void getAllCountriesTest() throws Exception {

    StudentFilter filter = new StudentFilter(null, null, null, null);

    doReturn(List.of(
      StudentEntity.builder()
        .name("Alex")
        .age(37).build(),
      StudentEntity.builder()
        .name("Stefan")
        .age(38).build()
    )).when(studentService).getFilteredStudents(filter);

    mvc.perform(get("/students"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isArray())
      .andExpect((ResultMatcher) jsonPath("$[0].name", CoreMatchers.is("Alex")));

    verify(studentService, times(1)).getFilteredStudents(filter);
  }

  @Test
  @DisplayName("POST /students")
  void postStudent() throws Exception {
    StudentEntity student = StudentEntity.builder()
      .name("Alex")
      .age(37).build();
    doReturn(student).when(studentService).addStudent(student);

    mvc.perform(MockMvcRequestBuilders.post("/students")
        .content(asJsonString(student))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.name").isString())
      .andExpect((ResultMatcher) jsonPath("$.name", CoreMatchers.is("Alex")))
      .andExpect(MockMvcResultMatchers.jsonPath("$.age").isNumber())
      .andExpect((ResultMatcher) jsonPath("$.age", CoreMatchers.is(37)));
  }

  public static String asJsonString(final Object obj) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Configuration
  static class TestBeans {

    @Bean
    StudentService studentService() {
      return mock(StudentService.class);
    }

    @Bean
    StudentMapper studentMapper() {
      return mock(StudentMapper.class);
    }
  }
}
