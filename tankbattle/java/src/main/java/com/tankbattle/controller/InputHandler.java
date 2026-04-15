package com.tankbattle.controller;

import java.util.Scanner;

/**
 * 输入处理器 - 处理键盘输入
 */
public class InputHandler {
    private Scanner scanner;
    
    public InputHandler() {
        scanner = new Scanner(System.in);
    }
    
    /**
     * 获取单个字符输入（非阻塞）
     */
    public char getCharInput() {
        try {
            if (System.in.available() > 0) {
                return (char) System.in.read();
            }
        } catch (Exception e) {
            // 忽略异常
        }
        return 0;
    }
    
    /**
     * 等待按键输入（阻塞）
     */
    public char waitForKey() {
        try {
            // 清空输入缓冲区
            while (System.in.available() > 0) {
                System.in.read();
            }
            
            // 等待输入
            return (char) System.in.read();
        } catch (Exception e) {
            return 0;
        }
    }
    
    /**
     * 获取数字输入
     */
    public int getNumberInput() {
        try {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
        } catch (Exception e) {
            // 忽略异常
        }
        return -1;
    }
    
    /**
     * 清理输入缓冲区
     */
    public void clearBuffer() {
        try {
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (Exception e) {
            // 忽略异常
        }
    }
    
    /**
     * 关闭扫描器
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
