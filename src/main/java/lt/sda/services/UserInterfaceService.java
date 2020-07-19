package lt.sda.services;

public interface UserInterfaceService {
    void displayMessage(String message, Object... args);
    int enterCode();
    boolean question(String message, Object... args);
}
