/**
 * UserNotFoundException.java
 * This exception is thrown when a user is not found
 */
package com.achievement.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("用户不存在");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}