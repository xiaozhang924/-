/**
 * PasswordMismatchException.java
 * This exception is thrown when the password does not match
 */
package com.achievement.exception;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super("密码错误");
    }

    public PasswordMismatchException(String message) {
        super(message);
    }

    public PasswordMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}