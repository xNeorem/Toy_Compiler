package it.esercitazione4.exceptions;

public class CallProcException  extends Exception{

  /**
   * Constructs a new exception with {@code null} as its detail message. The cause is not initialized,
   * and may subsequently be initialized by a call to {@link #initCause}.
   */
  public CallProcException() {
    super("I parametri forniti non sono conformi alla signature della procedura.");
  }

  /**
   * Constructs a new exception with the specified detail message.  The cause is not initialized, and
   * may subsequently be initialized by a call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *     {@link #getMessage()} method.
   */
  public CallProcException(String message) {
    super("I parametri forniti non sono conformi alla signature della procedura "+message+".");
  }
}
