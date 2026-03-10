/**
 * GeneratePasswordHash.java
 * This class is used to generate password hashes for updating the database script
 */
package com.achievement.util;

public class GeneratePasswordHash {
    public static void main(String[] args) {
        String password = "123456";
        String hashedPassword = PasswordUtil.hashPassword(password);
        System.out.println("Original password: " + password);
        System.out.println("Hashed password: " + hashedPassword);
        System.out.println("Password verification: " + PasswordUtil.verifyPassword(password, hashedPassword));
    }
}