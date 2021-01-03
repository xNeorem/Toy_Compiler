package it.esercitazione4.exceptions;

public class ReturnParamsException extends Exception{

  /**
   * Constructs a new exception with {@code null} as its detail message. The cause is not initialized,
   * and may subsequently be initialized by a call to {@link #initCause}.
   */
  public ReturnParamsException() {
    super("Esiste un errore nei tipi dei parametri di ritorno.");
  }

  /**
   * Constructs a new exception with the specified detail message.  The cause is not initialized, and
   * may subsequently be initialized by a call to {@link #initCause}.
   *
   * @param function the detail message. The detail message is saved for later retrieval by the
   *     {@link #getMessage()} method.
   */
  public ReturnParamsException(String function) {
    super("Esiste un errore nei tipi dei parametri di ritorno nella funzione "+function+".");
  }
}
