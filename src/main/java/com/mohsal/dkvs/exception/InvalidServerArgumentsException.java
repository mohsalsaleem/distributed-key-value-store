package com.mohsal.dkvs.exception;

/**
 * Author: Mohamed Saleem
 */
public class InvalidServerArgumentsException extends Exception {
    public InvalidServerArgumentsException() {
        super("Invalid server arguments. Should have host and port. Example: host=localhost port=9000");
    }
}
