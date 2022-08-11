package ro.fasttrackit.homework13unittesting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.homework13unittesting.model.StudentFilter;
import ro.fasttrackit.homework13unittesting.model.api.StudentCourse;
import ro.fasttrackit.homework13unittesting.model.entity.StudentEntity;
import ro.fasttrackit.homework13unittesting.model.mapper.StudentMapper;
import ro.fasttrackit.homework13unittesting.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("students")
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  private final StudentMapper mapper;

  @GetMapping
  List<StudentEntity> getFilterdStudents(StudentFilter filter) {
    return studentService.getFilteredStudents (filter);
  }

  @GetMapping("/{studentId}")
  StudentEntity getStudentById(@PathVariable String studentId){
    return studentService.getStudentById(studentId);
  }

  @GetMapping("/{studentId}/courses")
  List<StudentCourse> studentCourses(@PathVariable String studentId){
    return mapper.toApi(studentService.getStudentById (studentId));
  }

  @PostMapping
  StudentEntity addStudent (@RequestBody StudentEntity studentEntity){
    return studentService.addStudent(studentEntity);
  }
}
