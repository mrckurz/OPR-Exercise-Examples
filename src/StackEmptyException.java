public class StackEmptyException extends Exception {

    private int status;

    public StackEmptyException() {
        super();
        status = 0;
    }

    public StackEmptyException(String msg) {
        super(msg);
        status = 0;
    }

    public StackEmptyException(String msg, int status) {
        super(msg);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}