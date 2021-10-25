package vn.alpaca.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class StashErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() >= 400 && response.status() <= 499) {
            return new StashClientException(
                    response.status(),
                    response.reason(),
                    response.body()
            );
        }
        if (response.status() >= 500 && response.status() <= 599) {
            return new StashServerException(
                    response.status(),
                    response.reason()
            );
        }
        return defaultErrorDecoder.decode(s, response);
    }
}
