package ro.fasttrackit.homework13unittesting.exception;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String msg) {
    super (msg);
  }
}
