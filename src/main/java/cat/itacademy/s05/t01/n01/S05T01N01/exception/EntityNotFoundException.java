package cat.itacademy.s05.t01.n01.S05T01N01.exception;

public class EntityNotFoundException extends RuntimeException {
  public EntityNotFoundException(String message) {
    super(message);
  }
}
