package de.claudioaltamura.spring.boot.webflux.superheroes.backend;

public class ApplicationError {

  private final int errorCode;

  private final String errorMessage;

  public ApplicationError(int errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  @Override
  public String toString() {
    return "ApplicationError{"
        + "errorCode="
        + errorCode
        + ", errorMessage='"
        + errorMessage
        + '\''
        + '}';
  }
}
