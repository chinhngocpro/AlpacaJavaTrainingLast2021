package vn.alpaca.alpacajavatraininglast2021.object.exception;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
