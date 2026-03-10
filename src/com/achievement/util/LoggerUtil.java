/**
 * LoggerUtil.java
 * 日志工具类 - 提供系统日志记录功能
 * 
 * 功能说明：
 * 1. 提供多级别日志记录（DEBUG、INFO、WARN、ERROR）
 * 2. 日志自动记录到文件system.log
 * 3. 日志格式：时间戳 [级别] 消息内容
 * 4. 支持异常堆栈信息记录
 * 5. 线程安全，支持多线程环境
 * 
 * 日志级别说明：
 * - DEBUG: 调试信息，用于开发阶段的详细追踪
 * - INFO: 一般信息，记录系统正常运行状态
 * - WARN: 警告信息，潜在问题但不影响运行
 * - ERROR: 错误信息，记录异常和错误情况
 * 
 * 使用示例：
 * LoggerUtil.info("用户登录成功");
 * LoggerUtil.error("数据库连接失败", exception);
 * 
 * 日志文件位置：项目根目录/system.log
 * 
 * @author 学生成果管理系统开发组
 * @version 1.0
 */
package com.achievement.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerUtil {
    // 日志文件名称，保存在项目根目录
    private static final String LOG_FILE = "system.log";
    
    // 日志时间戳格式：年-月-日 时:分:秒
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    // PrintWriter对象，用于写入日志文件
    // 使用static确保整个应用程序共用一个日志输出流
    private static PrintWriter writer;

    // 静态代码块，在类加载时初始化日志输出流
    static {
        try {
            // 创建PrintWriter对象
            // new FileWriter(LOG_FILE, true): 以追加模式打开文件，不覆盖原有内容
            // true参数: 启用自动刷新，每次写入后立即刷新到文件
            writer = new PrintWriter(new FileWriter(LOG_FILE, true), true);
        } catch (IOException e) {
            // 如果日志文件创建失败，打印错误到控制台
            e.printStackTrace();
            System.err.println("初始化日志失败: " + e.getMessage());
        }
    }

    /**
     * 记录调试级别日志
     * 用于开发和测试阶段，记录详细的程序运行信息
     * 
     * @param message 调试消息
     */
    public static void debug(String message) {
        log("DEBUG", message);
    }

    /**
     * 记录信息级别日志
     * 用于记录系统正常运行的关键操作和状态变化
     * 如：用户登录、数据查询、业务操作完成等
     * 
     * @param message 信息消息
     */
    public static void info(String message) {
        log("INFO", message);
    }

    /**
     * 记录警告级别日志
     * 用于记录潜在问题，不影响系统运行但需要关注
     * 如：参数验证失败、资源即将耗尽等
     * 
     * @param message 警告消息
     */
    public static void warn(String message) {
        log("WARN", message);
    }

    /**
     * 记录错误级别日志
     * 用于记录系统错误和异常情况
     * 如：数据库连接失败、文件读写错误等
     * 
     * @param message 错误消息
     */
    public static void error(String message) {
        log("ERROR", message);
    }

    /**
     * 记录错误级别日志（带异常信息）
     * 除了记录错误消息，还会记录完整的异常堆栈信息
     * 便于问题定位和调试
     * 
     * @param message 错误消息
     * @param e 异常对象
     */
    public static void error(String message, Exception e) {
        log("ERROR", message);
        // 如果异常对象不为空，将异常堆栈信息写入日志
        if (e != null) {
            e.printStackTrace(writer);
        }
    }

    /**
     * 记录日志的核心方法（私有方法）
     * 格式：时间戳 [级别] 消息内容
     * 示例：2024-01-15 14:30:25 [INFO] 用户登录成功
     * 
     * 使用synchronized确保线程安全，避免多线程写入冲突
     * 
     * @param level 日志级别（DEBUG/INFO/WARN/ERROR）
     * @param message 日志消息内容
     */
    private static synchronized void log(String level, String message) {
        // 检查writer是否已初始化
        if (writer == null) {
            return;
        }
        
        // 生成当前时间戳
        String timestamp = DATE_FORMAT.format(new Date());
        
        // 按格式写入日志：时间戳 [级别] 消息
        writer.println(timestamp + " [" + level + "] " + message);
    }

    /**
     * 关闭日志输出流
     * 在应用程序退出前调用，确保所有日志都已写入文件
     * 释放文件资源，防止资源泄漏
     * 
     * 注意：关闭后不能再次使用，需要重启应用程序
     */
    public static synchronized void close() {
        if (writer != null) {
            writer.close();
            writer = null;
        }
    }
}