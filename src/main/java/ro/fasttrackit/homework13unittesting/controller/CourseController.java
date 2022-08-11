package ro.fasttrackit.homework13unittesting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.homework13unittesting.model.entity.CourseEntity;
import ro.fasttrackit.homework13unittesting.model.entity.CourseStudent;
import ro.fasttrackit.homework13unittesting.model.entity.StudentEntity;
import ro.fasttrackit.homework13unittesting.service.CourseService;

import java.util.List;
import java.util.concurrent.CompletionService;

@RestController
@RequestMapping("courses")
@RequiredArgsConstructor
public class CourseController {
  private final CourseService courseService;

  @GetMapping
  List<CourseEntity> getCourses(@RequestParam(required = false) String studentId) {
    return courseService.getAllCourses (studentId);
  }

  @GetMapping("{courseId}")
  CourseEntity getCourseById(@PathVariable String courseId) {
    return courseService.getCourseById (courseId);
  }

  @PostMapping
  CourseEntity addCourse(@RequestBody CourseEntity courseEntity) {
    return courseService.addCourse (courseEntity);
  }

  @GetMapping("/{courseId}/students")
  List<StudentEntity> getAllStudentForCourses(@PathVariable String courseId) {
    return courseService.getAllStudentForCourses (courseId);
  }

  @PostMapping("/{courseId}/students")
  CourseStudent addStudentToCourse(@PathVariable String courseId,
                                   @RequestBody String studentId) {
    return courseService.addStudentToCourse (courseId, studentId);
  }
}
