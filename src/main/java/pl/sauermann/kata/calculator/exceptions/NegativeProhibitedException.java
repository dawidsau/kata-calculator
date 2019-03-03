package pl.sauermann.kata.calculator.exceptions;

public class NegativeProhibitedException extends RuntimeException {

    public NegativeProhibitedException(String message) {
        super(message);
    }
}
