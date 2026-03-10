/**
 * DBUtil.java
 * 数据库工具类 - 提供数据库连接和资源管理功能
 * 
 * 功能说明：
 * 1. 管理数据库连接池，提供统一的连接获取方法
 * 2. 从配置文件（db.properties）读取数据库连接参数
 * 3. 提供资源释放方法，防止内存泄漏
 * 4. 使用单例模式确保配置只加载一次
 * 
 * 配置文件：src/db.properties
 * 配置项：
 * - url: 数据库连接地址
 * - username: 数据库用户名
 * - password: 数据库密码
 * - driver: JDBC驱动类名
 * 
 * 使用方式：
 * Connection conn = DBUtil.getConnection();  // 获取连接
 * // ... 执行数据库操作
 * DBUtil.close(rs, ps, conn);  // 释放资源
 * 
 * @author 学生成果管理系统开发组
 * @version 1.0
 */
package com.achievement.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil{
    // Properties对象，用于存储数据库配置信息
    private static Properties properties = new Properties();
    
    // 数据库连接URL（如：jdbc:mysql://localhost:3306/student_achievement_system）
    private static String URL;
    
    // 数据库用户名
    private static String USER;
    
    // 数据库密码
    private static String PASSWORD;

    // 静态代码块，在类加载时执行，用于初始化数据库配置
    // 只会执行一次，确保配置的单例性
    static {
        try {
            // 从类路径加载db.properties配置文件
            // getResourceAsStream()方法会在src目录及其子目录中查找文件
            InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            
            // 检查配置文件是否存在
            if (inputStream == null) {
                throw new RuntimeException("无法找到db.properties文件。");
            }
            
            // 加载配置文件内容到Properties对象
            properties.load(inputStream);
            
            // 从Properties对象中读取数据库连接参数
            URL = properties.getProperty("url");
            USER = properties.getProperty("username");
            PASSWORD = properties.getProperty("password");
            String driver = properties.getProperty("driver");
            
            // 加载MySQL JDBC驱动
            // 对于MySQL 8.x，驱动类名为：com.mysql.cj.jdbc.Driver
            // 对于MySQL 5.x，驱动类名为：com.mysql.jdbc.Driver
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库连接初始化失败: " + e.getMessage());
        }
    }

    /**
     * 获取数据库连接
     * 每次调用都会创建一个新的数据库连接
     * 
     * 注意事项：
     * 1. 使用完毕后必须调用close()方法关闭连接
     * 2. 频繁调用会影响性能，实际项目中建议使用连接池
     * 
     * @return 数据库连接对象
     * @throws RuntimeException 连接失败时抛出运行时异常
     */
    public static Connection getConnection() {
        try {
            // 使用DriverManager建立数据库连接
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // 记录连接成功日志
            LoggerUtil.info("数据库连接建立成功。");
            
            return conn;
        } catch (SQLException e) {
            // 记录连接失败日志
            LoggerUtil.error("数据库连接失败: " + e.getMessage(), e);
            
            // 抛出运行时异常，便于上层处理
            throw new RuntimeException("数据库连接失败。");
        }
    }

    /**
     * 关闭数据库资源（ResultSet、PreparedStatement、Connection）
     * 按照关闭顺序：ResultSet -> PreparedStatement -> Connection
     * 
     * 使用场景：查询操作后释放所有资源
     * 
     * @param rs 结果集对象
     * @param ps 预编译语句对象
     * @param conn 数据库连接对象
     */
    public static void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            // 关闭结果集
            if (rs != null) rs.close();
            
            // 关闭预编译语句
            if (ps != null) ps.close();
            
            // 关闭数据库连接
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库资源（PreparedStatement、Connection）
     * 按照关闭顺序：PreparedStatement -> Connection
     * 
     * 使用场景：增删改操作后释放资源（无ResultSet）
     * 
     * @param ps 预编译语句对象
     * @param conn 数据库连接对象
     */
    public static void close(PreparedStatement ps, Connection conn) {
        close(null, ps, conn);
    }

    /**
     * 关闭数据库连接
     * 
     * 使用场景：只需要关闭连接对象
     * 
     * @param conn 数据库连接对象
     */
    public static void close(Connection conn) {
        close(null, null, conn);
    }
}
