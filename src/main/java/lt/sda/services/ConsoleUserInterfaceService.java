package lt.sda.services;

import java.util.Scanner;

public class ConsoleUserInterfaceService implements UserInterfaceService{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void displayMessage(String message, Object... args) {
        System.out.printf(message + "\n", args);
    }

    @Override
    public int enterCode() {
        System.out.println("Enter code: ");
        return scanner.nextInt();
    }

    @Override
    public boolean question(String message, Object... args) {
        displayMessage(message + " (Y/n)", args);
        String next = scanner.next();
        String anwser = next;
        return anwser.toLowerCase().startsWith("y");
    }
}
