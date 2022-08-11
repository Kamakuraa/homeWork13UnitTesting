package ro.fasttrackit.homework13unittesting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.homework13unittesting.exception.ValidationException;
import ro.fasttrackit.homework13unittesting.repository.course.CourseRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CourseValidator {
  private final CourseRepository repository;

  private Optional<ValidationException> exists(String courseId) {
    return repository.existsById (courseId)
      ? Optional.empty ()
      : Optional.of (new ValidationException (List.of ("Course with id " + courseId + " doesn't exist")));
  }

  public void validateExistsOrThrow(String courseId) {
    exists (courseId).ifPresent (ex -> {
      throw ex;
    });
  }
}
