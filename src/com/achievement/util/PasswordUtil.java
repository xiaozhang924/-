/**
 * PasswordUtil.java
 * 该类提供密码加密和验证的工具方法
 */
package com.achievement.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtil {
    // 密码哈希算法
    private static final String ALGORITHM = "SHA-256";
    // 盐值长度
    private static final int SALT_LENGTH = 16;
    // 哈希迭代次数
    private static final int ITERATIONS = 10000;

    /**
     * 生成随机盐值
     * @return 随机盐值
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * 将字节数组转换为十六进制字符串
     * @return 十六进制字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 将十六进制字符串转换为字节数组
     * @param hexString 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexToBytes(String hexString) {
        int length = hexString.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }

    /**
     * 对密码进行哈希处理
     * @param password 原始密码
     * @param salt 盐值
     * @return 哈希后的密码
     */
    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.reset();
            digest.update(salt);
            byte[] hash = digest.digest(password.getBytes());

            // Iterative hashing
            for (int i = 0; i < ITERATIONS; i++) {
                digest.reset();
                hash = digest.digest(hash);
            }

            // 返回盐值和哈希值组合的字符串
            return bytesToHex(salt) + bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码哈希处理失败: " + e.getMessage());
        }
    }

    /**
     * 验证密码
     * @param password 原始密码
     * @param hashedPassword 哈希后的密码（包含盐值）
     * @return 是否匹配
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        // 从哈希后的密码中提取盐值
        byte[] salt = hexToBytes(hashedPassword.substring(0, SALT_LENGTH * 2));
        // 使用相同的盐值对输入的密码进行哈希处理
        String hashedInputPassword = hashPassword(password, salt);
        // 比较哈希值
        return hashedInputPassword.equals(hashedPassword);
    }

    /**
     * 简化的密码哈希方法，自动生成盐值
     * @param password 原始密码
     * @return 哈希后的密码（包含盐值）
     */
    public static String hashPassword(String password) {
        byte[] salt = generateSalt();
        return hashPassword(password, salt);
    }
}