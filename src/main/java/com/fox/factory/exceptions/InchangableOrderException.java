package com.fox.factory.exceptions;
/**
 * This class is a custom exception class that extends the Exception class.
 */

public class InchangableOrderException extends Exception{
// A constructor that takes a string as a parameter and passes it to the super class.
    public InchangableOrderException(String message) {
        super(message);
    }
}
