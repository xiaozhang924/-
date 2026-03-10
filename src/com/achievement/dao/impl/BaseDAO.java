/**
 * BaseDAO.java
 * 抽象基础DAO类 - 为所有DAO实现类提供通用的数据库操作方法
 * 
 * 功能说明：
 * 1. 封装数据库连接获取和资源释放的通用逻辑
 * 2. 提供executeUpdate()方法统一处理增删改操作
 * 3. 使用模板方法模式简化子类开发
 * 4. 所有具体的DAO实现类都应继承此类
 * 
 * @author 学生成果管理系统开发组
 * @version 1.0
 */
package com.achievement.dao.impl;

import com.achievement.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDAO {
    /**
     * 获取数据库连接
     * 封装DBUtil的连接获取方法，为子类提供统一的连接获取入口
     * 
     * @return 数据库连接对象
     */
    protected Connection getConnection() {
        return DBUtil.getConnection();
    }

    /**
     * 关闭数据库资源（完整版本）
     * 用于查询操作后释放所有资源
     * 
     * @param rs 结果集对象
     * @param ps 预编译语句对象
     * @param conn 数据库连接对象
     */
    protected void closeResources(ResultSet rs, PreparedStatement ps, Connection conn) {
        DBUtil.close(rs, ps, conn);
    }

    /**
     * 关闭数据库资源（简化版本）
     * 用于增删改操作后释放资源（无ResultSet）
     * 
     * @param ps 预编译语句对象
     * @param conn 数据库连接对象
     */
    protected void closeResources(PreparedStatement ps, Connection conn) {
        DBUtil.close(ps, conn);
    }

    /**
     * 关闭数据库连接
     * 只关闭连接对象
     * 
     * @param conn 数据库连接对象
     */
    protected void closeResources(Connection conn) {
        DBUtil.close(conn);
    }

    /**
     * 执行增删改操作（INSERT、UPDATE、DELETE）
     * 
     * 功能说明：
     * 1. 自动管理数据库连接的获取和释放
     * 2. 使用PreparedStatement防止SQL注入
     * 3. 支持可变参数，灵活处理不同数量的SQL参数
     * 4. 统一异常处理，转换为运行时异常
     * 
     * 使用示例：
     * executeUpdate("INSERT INTO users (name, age) VALUES (?, ?)", "张三", 20);
     * executeUpdate("UPDATE users SET age = ? WHERE id = ?", 21, 1);
     * executeUpdate("DELETE FROM users WHERE id = ?", 1);
     * 
     * @param sql SQL语句（可包含?占位符）
     * @param params SQL参数（可变参数，对应SQL中的?）
     * @return 受影响的行数
     * @throws RuntimeException 数据库操作失败时抛出
     */
    protected int executeUpdate(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // 获取数据库连接
            conn = getConnection();
            
            // 创建预编译语句
            ps = conn.prepareStatement(sql);
            
            // 设置SQL参数
            setParameters(ps, params);
            
            // 执行更新操作并返回受影响的行数
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库操作失败: " + e.getMessage());
        } finally {
            // 确保资源被释放
            closeResources(ps, conn);
        }
    }

    /**
     * 为PreparedStatement设置参数
     * 将可变参数数组按顺序设置到SQL语句的占位符中
     * 
     * 注意：JDBC参数索引从1开始，不是0
     * 
     * @param ps 预编译语句对象
     * @param params 参数数组
     * @throws SQLException SQL异常
     */
    private void setParameters(PreparedStatement ps, Object... params) throws SQLException {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                // JDBC参数索引从1开始，所以是i+1
                ps.setObject(i + 1, params[i]);
            }
        }
    }
}