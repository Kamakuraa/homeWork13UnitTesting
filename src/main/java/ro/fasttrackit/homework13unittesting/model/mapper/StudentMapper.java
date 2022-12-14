package ro.fasttrackit.homework13unittesting.model.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.homework13unittesting.model.api.StudentCourse;
import ro.fasttrackit.homework13unittesting.model.entity.CourseEntity;
import ro.fasttrackit.homework13unittesting.model.entity.CourseStudent;
import ro.fasttrackit.homework13unittesting.model.entity.StudentEntity;
import ro.fasttrackit.homework13unittesting.repository.course.CourseRepository;
import ro.fasttrackit.homework13unittesting.repository.course.CourseStudentRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class StudentMapper implements Mapper<List<StudentCourse>, StudentEntity>{
  private final CourseRepository courseRepository;
  private final CourseStudentRepository courseStudentRepository;

  @Override
  public List<StudentCourse> toApi(StudentEntity entity) {
    if (entity == null) {
      return null;
    }

    List<CourseStudent> assignedCourses = courseStudentRepository.findByStudentId(entity.getId());
    List<CourseEntity> courses = assignedCourses.stream()
      .map(assignedCourse -> courseRepository.findById(assignedCourse.getCourseId()))
      .filter(foundCourse -> foundCourse.isPresent())
      .map(foundCourse -> foundCourse.get())
      .collect(toList());
    return courses.stream().map(course -> new StudentCourse(entity.getName(), entity.getAge(), course.getDiscipline(),
        assignedCourses.stream().filter(assignedCourse -> assignedCourse.getCourseId().equals(course.getId()))
          .findFirst()
          .get().getGrade()))
      .collect(toList());
  }

  @Override
  public StudentEntity toEntity(List<StudentCourse> source) {
    return null;
  }
}
