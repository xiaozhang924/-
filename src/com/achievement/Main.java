
package com.achievement;

import com.achievement.view.LoginFrame;

import javax.swing.*;

public class Main {
    /**
     * 应用程序主入口方法
     * 工作流程：
     * 1. JVM调用此方法启动程序
     * 2. 使用SwingUtilities.invokeLater()在事件调度线程中创建GUI
     * 3. 创建并显示登录窗口
     * 4. 等待用户登录成功后跳转到相应角色的主界面
     */
    public static void main(String[] args) {
        // 在Swing事件调度线程（EDT）中启动应用程序
        // 这是Swing GUI编程的最佳实践，确保所有UI操作都在EDT中执行
        // 避免多线程并发访问UI组件导致的线程安全问题
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 创建并显示登录界面
                // LoginFrame是系统的第一个用户界面
                // 用户需要先登录才能访问系统功能
                new LoginFrame();
            }
        });
    }
}