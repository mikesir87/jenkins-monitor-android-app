package com.nerdwin15.android.jenkinsmonitor.task;

/**
 * A utility class that is used to wrap up results for an AsyncTask. This is
 * created because an AsyncTask can only return one type.  This class allows
 * a return type of any specified value (the generic type) or an exception in
 * the case something happens during the task execution. That way, exceptions
 * don't have to bubble out and can be handled.
 *
 * It is expected that a TaskResult will represent only one result - either that
 * if a valid result (the generic type result is provided) or that an exception
 * was returned.  Both shouldn't be used at the same time.
 *
 * @author Michael Irwin (mikesir87)
 */
public class TaskResult<T> {

  private final T result;
  private final Throwable error;
  
  /**
   * Creates a result that represents a successful execution of the AsyncTask
   * and makes the provided result object available
   * @param result The result to use
   */
  public TaskResult(T result) {
    this.result = result;
    this.error = null;
  }
  
  /**
   * Creates a result that represents a failure in the AsyncTask
   * @param error The exception that occurred and should be used
   */
  public TaskResult(Throwable error) {
    this.error = error;
    this.result = null;
  }
  
  /**
   * Does this result represent an error or a valid result?
   * @return True if an error occurred. Error can be found in getError
   */
  public boolean errorOccurred() {
    return error != null;
  }

  /**
   * Gets the {@code result} property.
   */
  public T getResult() {
    return result;
  }

  /**
   * Gets the {@code error} property.
   */
  public Throwable getError() {
    return error;
  }
  
}
