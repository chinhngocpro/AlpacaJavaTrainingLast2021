package vn.alpaca.alpacajavatraininglast2021.wrapper.response;


public class ErrorResponse extends AbstractResponse {

    public ErrorResponse(int errorCode) {
        super(errorCode);
    }

    public ErrorResponse(int errorCode, String message) {
        super(errorCode);
        setMessage(message);
    }
}
