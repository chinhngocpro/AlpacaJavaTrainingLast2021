package vn.alpaca.exception;

public class StashServerException extends RuntimeException {

    int status;
    String reason;

    public StashServerException(int status, String reason) {
        this.status = status;
        this.reason = reason;
    }
}
