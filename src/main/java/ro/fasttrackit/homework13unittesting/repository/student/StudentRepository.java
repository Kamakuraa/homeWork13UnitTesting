package ro.fasttrackit.homework13unittesting.repository.student;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.homework13unittesting.model.entity.StudentEntity;
@Repository
public interface StudentRepository extends MongoRepository<StudentEntity, String> {
}
