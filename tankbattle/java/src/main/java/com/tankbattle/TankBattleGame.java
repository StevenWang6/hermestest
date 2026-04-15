package com.tankbattle;

import com.tankbattle.controller.GameController;
import com.tankbattle.model.GameConfig;

/**
 * 坦克大战游戏主类
 */
public class TankBattleGame {
    
    public static void main(String[] args) {
        System.out.println("🎮 启动 Java 坦克大战游戏...");
        System.out.println("版本: 1.0.0 | 作者: StevenWang6");
        System.out.println();
        
        // 显示使能开关初始状态
        GameConfig config = GameConfig.getInstance();
        System.out.println("初始使能开关状态:");
        System.out.println(config.getSwitchStatus());
        System.out.println();
        
        // 创建并启动游戏控制器
        GameController gameController = new GameController();
        
        try {
            gameController.startGame();
        } catch (Exception e) {
            System.err.println("游戏运行出错: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("游戏结束，感谢游玩！");
    }
}
