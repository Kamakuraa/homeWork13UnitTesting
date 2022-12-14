package ro.fasttrackit.homework13unittesting.repository.student;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import ro.fasttrackit.homework13unittesting.model.StudentFilter;
import ro.fasttrackit.homework13unittesting.model.entity.CourseStudent;
import ro.fasttrackit.homework13unittesting.model.entity.StudentEntity;
import ro.fasttrackit.homework13unittesting.repository.course.CourseStudentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataMongoTest(includeFilters = @ComponentScan.Filter(Component.class))
//@ContextConfiguration(classes = StudentDao.class)
class StudentDaoTest {
  @Autowired
  private StudentDao studentDao;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CourseStudentRepository courseStudentRepository;

  @Test
  @DisplayName("WHEN getAll is called with an empty filter THEN all students are returned")
  void getAllWithoutFiltersTest() {
    studentRepository.saveAll(List.of(
      StudentEntity.builder()
        .name("Alex")
        .age(37).build(),
      StudentEntity.builder()
        .name("Stefan")
        .age(38).build()
    ));

    StudentFilter filter = new StudentFilter(null, null, null, null);

    List<StudentEntity> result = studentDao.getAll(filter);

    assertThat(result).isNotEmpty();
    assertThat(result.size()).isEqualTo(2);

    assertThat(result.get(0)).extracting("name", "age")
      .containsExactly("Alex", 37);
    assertThat(result.get(1)).extracting("name", "age")
      .containsExactly("Stefan", 38);
  }

  @Test
  @DisplayName("WHEN getAll is called with a filter THEN all filtered students are returned")
  void getAllWithFiltersTest() {
    studentRepository.saveAll(List.of(
      StudentEntity.builder()
        .name("Alex")
        .age(37).build(),
      StudentEntity.builder()
        .name("Stefan")
        .age(38).build()
    ));

    StudentFilter filter = new StudentFilter("Luna", 1, null, null);

    List<StudentEntity> result = studentDao.getAll(filter);

    assertThat(result).isNotEmpty();
    assertThat(result.size()).isEqualTo(1);

    assertThat(result.get(0)).extracting("name", "age")
      .containsExactly("Stefan", 38);
  }

  @Test
  @DisplayName("WHEN getAll is called with a course id THEN all students registered under the course are returned")
  void getAllWithCourseIdTest() {
    studentRepository.saveAll(List.of(
      StudentEntity.builder()
        .name("Alex")
        .age(37)
        .id("1").build(),
      StudentEntity.builder()
        .name("Stefan")
        .age(38)
        .id("2").build()
    ));

    courseStudentRepository.save(CourseStudent.builder().courseId("curs1").studentId("2").build());

    List<StudentEntity> result = studentDao.getAll("curs1");

    assertThat(result).isNotEmpty();
    assertThat(result.size()).isEqualTo(1);

    assertThat(result.get(0)).extracting("name", "age")
      .containsExactly("Stefan", 38);
  }

  @AfterEach
  void cleanup() {
    studentRepository.deleteAll();
  }
}
