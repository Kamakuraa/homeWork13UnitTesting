package ro.fasttrackit.homework13unittesting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.homework13unittesting.exception.ValidationException;
import ro.fasttrackit.homework13unittesting.repository.student.StudentRepository;


import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StudentValidator {
  private final StudentRepository repository;

  private Optional<ValidationException> exists(String studentId){
    return repository.existsById (studentId)
      ? Optional.empty ()
      : Optional.of (new ValidationException (List.of (" Student with id " + studentId + " doesn't exist")));
  }

  public void validateExistsOrThrow(String studentId){
    exists (studentId).ifPresent (ex->{
      throw ex;
    });
  }

}
