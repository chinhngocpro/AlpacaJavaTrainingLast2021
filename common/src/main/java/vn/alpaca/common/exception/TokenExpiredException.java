package vn.alpaca.common.exception;

public class TokenExpiredException extends RuntimeException {

  public TokenExpiredException() {}

  public TokenExpiredException(String message) {
    super(message);
  }
}
