package ro.fasttrackit.homework13unittesting;


import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ro.fasttrackit.homework13unittesting.model.entity.StudentEntity;
import ro.fasttrackit.homework13unittesting.repository.student.StudentRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CoursesTest {
  @Autowired
  MockMvc mvc;
  @Autowired
  private StudentRepository repository;

  @SneakyThrows
  @Test
  @DisplayName("GET /students")
  void getStudentsTest() {
    repository.saveAll(List.of(
      StudentEntity.builder()
        .name("Alex")
        .age(37).build(),
      StudentEntity.builder()
        .name("Stefan")
        .age(38).build()
    ));
    mvc.perform(get("/students"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$.[0].name", CoreMatchers.is("Alex")));
  }

  @AfterEach
  void cleanup() {
    repository.deleteAll();
  }
}
