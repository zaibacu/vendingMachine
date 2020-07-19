package lt.sda;

public interface Command {
    void execute();
    void rollback();
}
