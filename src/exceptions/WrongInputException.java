/**
 * PSL Challenge - Minesweeper Game
 * Universidad Icesi
 *
 * @author Brayan Henao
 * @version 1.0
 */

package exceptions;

public class WrongInputException extends Exception {

    // ATTRIBUTES

    /**
     * The message of the exception.
     */
    private String message;

    /**
     * Constructor of the WrongInputException class.
     *
     * @param message - The message of the exception.
     */
    public WrongInputException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Method to get the exception message.
     *
     * @return String - the message of the exception.
     */
    @Override
    public String getMessage() {
        return message;
    }
}
