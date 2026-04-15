package com.tankbattle.utils;

import com.tankbattle.model.GameConfig;

/**
 * 游戏工具类
 */
public class GameUtils {
    
    /**
     * 计算两点之间的距离
     */
    public static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    
    /**
     * 检查位置是否有效（在地图范围内且不是障碍物）
     */
    public static boolean isValidPosition(int x, int y, char[][] map) {
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length) {
            return false;
        }
        return map[x][y] != '#';
    }
    
    /**
     * 生成随机位置
     */
    public static int[] generateRandomPosition(int maxX, int maxY) {
        int x = (int) (Math.random() * maxX);
        int y = (int) (Math.random() * maxY);
        return new int[]{x, y};
    }
    
    /**
     * 格式化时间（毫秒转换为分:秒）
     */
    public static String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    
    /**
     * 显示游戏帮助信息
     */
    public static void showHelp() {
        System.out.println("=== 游戏帮助 ===");
        System.out.println("控制键:");
        System.out.println("  W/A/S/D - 移动坦克");
        System.out.println("  空格键   - 发射炮弹");
        System.out.println("  P       - 暂停/继续游戏");
        System.out.println("  T       - 开关设置菜单");
        System.out.println("  ESC     - 退出游戏");
        System.out.println();
        System.out.println("游戏目标:");
        System.out.println("  击败敌人坦克获得分数");
        System.out.println("  避免被敌人击中");
        System.out.println("  破坏障碍物开辟道路");
    }
    
    /**
     * 显示使能开关说明
     */
    public static void showSwitchHelp() {
        GameConfig config = GameConfig.getInstance();
        System.out.println("=== 使能开关说明 ===");
        System.out.println("开关功能:");
        System.out.println("  1. 音效: " + (config.isEnableSound() ? "开启" : "关闭") + 
                          " - 控制游戏音效");
        System.out.println("  2. 碰撞检测: " + (config.isEnableCollision() ? "开启" : "关闭") + 
                          " - 控制炮弹碰撞检测");
        System.out.println("  3. AI: " + (config.isEnableAI() ? "开启" : "关闭") + 
                          " - 控制敌人AI行为");
        System.out.println("  4. 道具系统: " + (config.isEnablePowerups() ? "开启" : "关闭") + 
                          " - 控制道具生成");
        System.out.println("  5. 调试模式: " + (config.isEnableDebug() ? "开启" : "关闭") + 
                          " - 显示调试信息");
        System.out.println();
        System.out.println("在开关菜单中使用数字键 1-5 切换开关状态");
    }
}
