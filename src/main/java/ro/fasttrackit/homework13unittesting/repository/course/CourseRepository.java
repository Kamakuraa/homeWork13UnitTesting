package ro.fasttrackit.homework13unittesting.repository.course;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.homework13unittesting.model.entity.CourseEntity;
@Repository
public interface CourseRepository extends MongoRepository<CourseEntity, String> {
}
