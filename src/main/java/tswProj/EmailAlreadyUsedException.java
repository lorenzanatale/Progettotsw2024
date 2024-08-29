package tswProj;

public class EmailAlreadyUsedException extends RuntimeException{
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
