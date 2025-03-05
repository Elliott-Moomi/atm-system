package za.co.nesloedufy.codepotatoes.atm.atmexceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
