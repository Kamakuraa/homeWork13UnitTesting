package ro.fasttrackit.homework13unittesting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import ro.fasttrackit.homework13unittesting.exception.ResourceNotFoundException;
import ro.fasttrackit.homework13unittesting.model.entity.CourseEntity;
import ro.fasttrackit.homework13unittesting.model.entity.CourseStudent;
import ro.fasttrackit.homework13unittesting.model.entity.StudentEntity;
import ro.fasttrackit.homework13unittesting.repository.course.CourseDao;
import ro.fasttrackit.homework13unittesting.repository.course.CourseRepository;
import ro.fasttrackit.homework13unittesting.repository.course.CourseStudentRepository;
import ro.fasttrackit.homework13unittesting.repository.student.StudentDao;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CourseService {
  private final CourseDao courseDao;
  private final CourseRepository courseRepository;
  private final CourseValidator courseValidator;
  private final StudentDao studentDao;
  private final CourseStudentRepository courseStudentRepository;

  public List<CourseEntity> getAllCourses(String studentId) {
    if (studentId != null) {
      return courseDao.getAll (studentId);
    } else {
      return courseRepository.findAll ();
    }
  }

  public CourseEntity getCourseById(String courseId) {
    courseValidator.validateExistsOrThrow (courseId);
    return courseRepository.findById (courseId)
      .orElseThrow (()-> new ResourceNotFoundException ("Could not found course with id " + courseId));
  }

  public CourseEntity addCourse(CourseEntity courseEntity) {
    return courseRepository.save (courseEntity);
  }

  public List<StudentEntity> getAllStudentForCourses(String courseId) {
    return studentDao.getAll (courseId);
  }

  public CourseStudent addStudentToCourse(String courseId, String studentId) {
    courseValidator.validateExistsOrThrow (courseId);
    courseValidator.validateExistsOrThrow (studentId);

    CourseStudent newCourseStudent = new CourseStudent (courseId, studentId, 0);
    return courseStudentRepository.save (newCourseStudent);
  }
}
