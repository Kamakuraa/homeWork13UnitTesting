package ro.fasttrackit.homework13unittesting.repository.student;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.homework13unittesting.model.StudentFilter;
import ro.fasttrackit.homework13unittesting.model.entity.CourseStudent;
import ro.fasttrackit.homework13unittesting.model.entity.StudentEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StudentDao {
  private final MongoTemplate mongo;

  public List<StudentEntity> getAll(StudentFilter filter) {
    Criteria criteria = new Criteria ();

    Optional.ofNullable (filter.getName ())
      .ifPresent (name -> criteria.and ("name").is (name));

    Optional.ofNullable (filter.getMinAge ())
      .ifPresent (minAge -> criteria.and ("age").is (minAge));

    Optional.ofNullable (filter.getMaxAge ())
      .ifPresent (maxAge -> criteria.and ("age").is (maxAge));

    Optional.ofNullable (filter.getStudentId ())
      .ifPresent (studentId -> criteria.and ("studentId").is (studentId));

    Query query = Query.query (criteria);
    return mongo.find (query, StudentEntity.class);
  }

  public List<StudentEntity> getAll(String courseId) {
    Criteria courseStudentCriteria = new Criteria ();

    Optional.ofNullable (courseId)
      .ifPresent (course -> courseStudentCriteria.and ("courseId").is (course));

    Query courseStudentQuery = Query.query (courseStudentCriteria);
    List<CourseStudent> assignedCourses = mongo.find (courseStudentQuery, CourseStudent.class);

    Criteria studentCriteria = new Criteria ();

    studentCriteria.and ("id").in (assignedCourses.stream ()
      .map (assignedCourse -> assignedCourse.getCourseId ())
      .collect (Collectors.toList ()));

    Query studentQuery = Query.query (studentCriteria);
    return mongo.find (studentQuery, StudentEntity.class);

  }

}
