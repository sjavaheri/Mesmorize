package ca.montreal.mesmorize.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class handles all the exceptions thrown in the application
 * 
 * @author Shidan Javaheri
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * This method handles all the times we throw a Global Exception
   * 
   * @param ex the thrown exception
   * @return a response entity that contains the error message and http status
   * @author Shidan Javaheri
   */
  @ExceptionHandler(GlobalException.class)
  public ResponseEntity<String> handleGlobalException(GlobalException ex) {
    return new ResponseEntity<String>(ex.getMessage(), ex.getStatus());
  }
}
