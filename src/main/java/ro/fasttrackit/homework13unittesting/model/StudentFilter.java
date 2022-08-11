package ro.fasttrackit.homework13unittesting.model;

import lombok.Value;

@Value
public class StudentFilter {
  String name;
  Integer minAge;
  Integer maxAge;
  String StudentId;
}
