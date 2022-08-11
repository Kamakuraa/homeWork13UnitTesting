package ro.fasttrackit.homework13unittesting.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "students")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentEntity {
  @Id
  private String id;

  private String name;

  private Integer age;
}
