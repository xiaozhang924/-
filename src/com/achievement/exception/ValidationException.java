/**
 * ValidationException.java
 * This exception is thrown when input validation fails
 */
package com.achievement.exception;

public class ValidationException extends RuntimeException {
    public ValidationException() {
        super("输入验证失败");
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}