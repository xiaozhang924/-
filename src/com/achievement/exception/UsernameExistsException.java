/**
 * UsernameExistsException.java
 * This exception is thrown when a username already exists
 */
package com.achievement.exception;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException() {
        super("用户名已存在");
    }

    public UsernameExistsException(String message) {
        super(message);
    }

    public UsernameExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}