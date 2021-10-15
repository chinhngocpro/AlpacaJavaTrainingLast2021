package vn.alpaca.response.exception;

import feign.Response;

public class StashClientException extends RuntimeException {

    int status;
    String reason;
    Response.Body body;


    public StashClientException
            (int status, String reason, Response.Body body) {
        this.status = status;
        this.reason = reason;
        this.body = body;
    }
}
