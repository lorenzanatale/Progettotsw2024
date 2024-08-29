package tswProj;

public class RuntimeSQLException extends RuntimeException {
	
    public RuntimeSQLException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuntimeSQLException(String message) {
        super(message);
    }
}
