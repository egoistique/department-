package department.exception;

public class ORMException extends RuntimeException{
    public ORMException(String message) {
        super(message);
    }

    public void printMessage() {
        System.out.println("Неуспешная операция: " + getMessage());
    }
}