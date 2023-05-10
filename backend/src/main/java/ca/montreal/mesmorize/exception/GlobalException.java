package ca.montreal.mesmorize.exception;

import org.springframework.http.HttpStatus;

/**
 * This class is the exception class that we will use for all exceptions
 * @author Shidan Javaheri 
 */

@SuppressWarnings("serial")
public class GlobalException extends RuntimeException {

  /** this exception has an HTTP status in addition to a method */
  private HttpStatus status;

  /**
   * This constructor creates the Global Exception 
   * All exceptions thrown will be Global Exceptions
   * @param status the http status of the failure
   * @param message the error message
   * @author Shidan Javaheri 
   */
  public GlobalException(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }

  /**
   * Get the http status of the failure
   *
   * @return the http status of the failure
   * @author Shidan Javaheri 
   */
  public HttpStatus getStatus() {
    return status;
  }
}
