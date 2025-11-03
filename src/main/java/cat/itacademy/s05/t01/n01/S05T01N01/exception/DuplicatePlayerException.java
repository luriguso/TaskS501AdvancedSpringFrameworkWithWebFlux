package cat.itacademy.s05.t01.n01.S05T01N01.exception;

public class DuplicatePlayerException extends RuntimeException {
    public DuplicatePlayerException(String message) {
        super(message);
    }
}
