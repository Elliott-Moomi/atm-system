package za.co.nesloedufy.codepotatoes.atm.atmexceptions;

public class InvalidLoginException extends Exception {
    public InvalidLoginException(String message) {
        super(message);
    }
}
