/**
 * ValidationUtil.java
 * 数据验证工具类 - 提供各种数据格式验证方法
 * 
 * 功能说明：
 * 1. 验证用户输入数据的合法性和格式正确性
 * 2. 提供邮箱、手机号、用户名、密码等常用验证方法
 * 3. 使用正则表达式进行格式匹配，确保数据质量
 * 4. 避免在业务逻辑中重复编写验证代码
 * 
 * 使用场景：
 * - 用户注册时验证输入信息
 * - 数据修改时验证新数据格式
 * - 表单提交前的客户端验证
 * 
 * @author 学生成果管理系统开发组
 * @version 1.0
 */
package com.achievement.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    // 邮箱格式正则表达式（符合标准邮箱格式）
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    
    // 手机号格式正则表达式（中国大陆11位手机号，1开头）
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    
    // 用户名格式正则表达式（3-20位字母、数字或下划线）
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    
    // 密码长度正则表达式（6-20位任意字符）
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{6,20}$");

    /**
     * 验证字符串是否非空
     * 检查字符串不为null且去除空格后不为空串
     * 
     * @param value 待验证的字符串
     * @return 验证通过返回true，否则返回false
     */
    public static boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * 验证邮箱格式是否正确
     * 格式要求：username@domain.com
     * 示例：zhangsan@test.com
     * 
     * @param email 待验证的邮箱地址
     * @return 格式正确返回true，否则返回false
     */
    public static boolean isValidEmail(String email) {
        if (!isNotNullOrEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 验证手机号格式是否正确
     * 格式要求：中国大陆11位手机号，1开头，第二位为3-9
     * 示例：13812345678、15987654321
     * 
     * @param phone 待验证的手机号码
     * @return 格式正确返回true，否则返回false
     */
    public static boolean isValidPhone(String phone) {
        if (!isNotNullOrEmpty(phone)) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * 验证用户名格式是否正确
     * 格式要求：3-20位，只能包含字母、数字和下划线
     * 示例：zhangsan、user123、test_user
     * 
     * @param username 待验证的用户名
     * @return 格式正确返回true，否则返回false
     */
    public static boolean isValidUsername(String username) {
        if (!isNotNullOrEmpty(username)) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * 验证密码长度是否符合要求
     * 长度要求：6-20位任意字符
     * 建议：包含大小写字母、数字和特殊字符以提高安全性
     * 
     * @param password 待验证的密码
     * @return 长度符合返回true，否则返回false
     */
    public static boolean isValidPassword(String password) {
        if (!isNotNullOrEmpty(password)) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * 验证整数是否在指定范围内
     * 常用于验证年份、年龄等数值型数据
     * 
     * @param value 待验证的整数值
     * @param min 最小值（包含）
     * @param max 最大值（包含）
     * @return 在范围内返回true，否则返回false
     */
    public static boolean isValidIntegerRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * 验证字符串长度是否在指定范围内
     * 常用于验证姓名、地址等文本数据的长度
     * 
     * @param value 待验证的字符串
     * @param min 最小长度（包含）
     * @param max 最大长度（包含）
     * @return 长度在范围内返回true，否则返回false
     */
    public static boolean isValidStringLength(String value, int min, int max) {
        if (!isNotNullOrEmpty(value)) {
            return false;
        }
        int length = value.length();
        return length >= min && length <= max;
    }
}