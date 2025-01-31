package trangtt.glow.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String userId) {
        super("You can delete only your own profile");
    }
}
