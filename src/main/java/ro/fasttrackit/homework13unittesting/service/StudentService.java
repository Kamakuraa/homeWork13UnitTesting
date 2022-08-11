package ro.fasttrackit.homework13unittesting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.homework13unittesting.exception.ResourceNotFoundException;
import ro.fasttrackit.homework13unittesting.model.StudentFilter;
import ro.fasttrackit.homework13unittesting.model.entity.StudentEntity;
import ro.fasttrackit.homework13unittesting.repository.student.StudentDao;
import ro.fasttrackit.homework13unittesting.repository.student.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
  private final StudentDao dao;
  private final StudentValidator validator;
  private final StudentRepository studentRepository;

  public List<StudentEntity> getFilteredStudents(StudentFilter filter) {
    return dao.getAll (filter);
  }

  public StudentEntity getStudentById(String studentId) {
    validator.validateExistsOrThrow (studentId);
    return studentRepository.findById (studentId)
      .orElseThrow (() -> new ResourceNotFoundException ("Could not find student with id " + studentId));
  }

  public StudentEntity addStudent(StudentEntity studentEntity) {
    return studentRepository.save (studentEntity);
  }
}
