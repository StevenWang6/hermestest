package com.tankbattle.view;

import com.tankbattle.model.Tank;
import com.tankbattle.model.Bullet;
import com.tankbattle.model.GameConfig;
import java.util.List;

/**
 * 控制台渲染器 - 在控制台显示游戏画面
 */
public class ConsoleRenderer {
    private static final String RESET = "\033[0m";
    private static final String RED = "\033[31m";
    private static final String GREEN = "\033[32m";
    private static final String YELLOW = "\033[33m";
    private static final String BLUE = "\033[34m";
    private static final String CYAN = "\033[36m";
    private static final String WHITE = "\033[37m";
    
    private GameConfig config;
    
    public ConsoleRenderer() {
        this.config = GameConfig.getInstance();
    }
    
    /**
     * 清空控制台
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    /**
     * 渲染游戏画面
     */
    public void renderGame(Tank playerTank, Tank enemyTank, List<Bullet> bullets, 
                          char[][] map, int score, boolean gameOver) {
        clearScreen();
        
        // 显示游戏标题
        System.out.println(CYAN + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║         🎮 JAVA 坦克大战游戏          ║" + RESET);
        System.out.println(CYAN + "╚════════════════════════════════════════╝" + RESET);
        
        // 显示游戏状态
        System.out.println(YELLOW + "得分: " + score + " | 玩家生命: " + 
                          (playerTank.isAlive() ? GREEN + playerTank.getHealth() + "❤️" : RED + "死亡") + 
                          RESET + " | 敌人生命: " + 
                          (enemyTank.isAlive() ? RED + enemyTank.getHealth() + "💀" : GREEN + "死亡") + RESET);
        
        System.out.println();
        
        // 渲染地图
        renderMap(playerTank, enemyTank, bullets, map);
        
        System.out.println();
        
        // 显示控制说明
        if (!gameOver) {
            System.out.println(WHITE + "控制键: " + 
                              GREEN + "WASD" + WHITE + "移动 | " + 
                              GREEN + "空格" + WHITE + "射击 | " + 
                              GREEN + "P" + WHITE + "暂停 | " + 
                              GREEN + "T" + WHITE + "开关菜单 | " + 
                              GREEN + "ESC" + WHITE + "退出" + RESET);
        } else {
            System.out.println(RED + "游戏结束! 按 R 重新开始，按 ESC 退出" + RESET);
        }
        
        // 如果调试模式开启，显示额外信息
        if (config.isEnableDebug()) {
            System.out.println(YELLOW + "[调试信息] 玩家位置: (" + playerTank.getX() + "," + playerTank.getY() + 
                              ") 敌人位置: (" + enemyTank.getX() + "," + enemyTank.getY() + 
                              ") 炮弹数: " + bullets.size() + RESET);
        }
    }
    
    /**
     * 渲染地图
     */
    private void renderMap(Tank playerTank, Tank enemyTank, List<Bullet> bullets, char[][] map) {
        int width = map.length;
        int height = map[0].length;
        
        // 上边框
        System.out.print(CYAN + "┌");
        for (int i = 0; i < width; i++) {
            System.out.print("─");
        }
        System.out.println("┐" + RESET);
        
        // 地图内容
        for (int y = 0; y < height; y++) {
            System.out.print(CYAN + "│" + RESET);
            for (int x = 0; x < width; x++) {
                char cell = ' ';
                
                // 检查玩家坦克
                if (playerTank.getX() == x && playerTank.getY() == y && playerTank.isAlive()) {
                    cell = playerTank.getSymbol();
                    System.out.print(GREEN + cell + RESET);
                    continue;
                }
                
                // 检查敌人坦克
                if (enemyTank.getX() == x && enemyTank.getY() == y && enemyTank.isAlive()) {
                    cell = enemyTank.getSymbol();
                    System.out.print(RED + cell + RESET);
                    continue;
                }
                
                // 检查炮弹
                boolean hasBullet = false;
                for (Bullet bullet : bullets) {
                    if (bullet.isActive() && bullet.getX() == x && bullet.getY() == y) {
                        cell = '•';
                        System.out.print(YELLOW + cell + RESET);
                        hasBullet = true;
                        break;
                    }
                }
                if (hasBullet) continue;
                
                // 检查地图元素
                if (map[x][y] == '#') {
                    cell = '#';
                    System.out.print(BLUE + cell + RESET);
                } else if (map[x][y] == 'X') {
                    cell = 'X';
                    System.out.print(WHITE + cell + RESET);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println(CYAN + "│" + RESET);
        }
        
        // 下边框
        System.out.print(CYAN + "└");
        for (int i = 0; i < width; i++) {
            System.out.print("─");
        }
        System.out.println("┘" + RESET);
    }
    
    /**
     * 显示使能开关菜单
     */
    public void renderSwitchMenu() {
        clearScreen();
        
        System.out.println(CYAN + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║           使能开关设置菜单            ║" + RESET);
        System.out.println(CYAN + "╚════════════════════════════════════════╝" + RESET);
        System.out.println();
        
        System.out.println(GameConfig.getInstance().getSwitchStatus());
        System.out.println();
        
        System.out.println(YELLOW + "使用数字键 1-5 切换开关状态" + RESET);
        System.out.println(GREEN + "按 Enter 确认并返回游戏" + RESET);
        System.out.println(RED + "按 ESC 取消并返回游戏" + RESET);
    }
    
    /**
     * 显示暂停菜单
     */
    public void renderPauseMenu() {
        System.out.println();
        System.out.println(YELLOW + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(YELLOW + "║             游戏已暂停                ║" + RESET);
        System.out.println(YELLOW + "╚════════════════════════════════════════╝" + RESET);
        System.out.println(WHITE + "按 P 继续游戏" + RESET);
        System.out.println(WHITE + "按 T 进入开关设置" + RESET);
        System.out.println(WHITE + "按 ESC 退出游戏" + RESET);
    }
    
    /**
     * 显示游戏开始画面
     */
    public void renderStartScreen() {
        clearScreen();
        
        System.out.println(CYAN + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║         🎮 JAVA 坦克大战游戏          ║" + RESET);
        System.out.println(CYAN + "╚════════════════════════════════════════╝" + RESET);
        System.out.println();
        
        // 显示坦克图案
        System.out.println(GREEN + "   ██████╗   玩家坦克: ██████╗" + RESET);
        System.out.println(GREEN + "  ██╔════╝            ██╔════╝" + RESET);
        System.out.println(GREEN + "  ██║                 ██║" + RESET);
        System.out.println(GREEN + "  ██║                 ██║" + RESET);
        System.out.println(GREEN + "  ╚██████╗            ╚██████╗" + RESET);
        System.out.println(GREEN + "   ╚═════╝             ╚═════╝" + RESET);
        
        System.out.println();
        System.out.println(RED + "   ██████╗   敌人坦克: ██████╗" + RESET);
        System.out.println(RED + "  ██╔════╝            ██╔════╝" + RESET);
        System.out.println(RED + "  ██║                 ██║" + RESET);
        System.out.println(RED + "  ██║                 ██║" + RESET);
        System.out.println(RED + "  ╚██████╗            ╚██████╗" + RESET);
        System.out.println(RED + "   ╚═════╝             ╚═════╝" + RESET);
        
        System.out.println();
        System.out.println(YELLOW + "游戏说明:" + RESET);
        System.out.println(WHITE + "• 控制绿色坦克(WASD移动，空格射击)" + RESET);
        System.out.println(WHITE + "• 击败红色敌人坦克获得分数" + RESET);
        System.out.println(WHITE + "• 按 T 键可以调整游戏功能开关" + RESET);
        System.out.println();
        System.out.println(GREEN + "按 Enter 开始游戏" + RESET);
        System.out.println(RED + "按 ESC 退出" + RESET);
    }
    
    /**
     * 显示游戏结束画面
     */
    public void renderGameOverScreen(int score, boolean win) {
        clearScreen();
        
        System.out.println(CYAN + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║             游戏结束                  ║" + RESET);
        System.out.println(CYAN + "╚════════════════════════════════════════╝" + RESET);
        System.out.println();
        
        if (win) {
            System.out.println(GREEN + "   🎉 恭喜你获胜! 🎉" + RESET);
            System.out.println(GREEN + "   你击败了所有敌人!" + RESET);
        } else {
            System.out.println(RED + "   💀 游戏失败! 💀" + RESET);
            System.out.println(RED + "   你的坦克被摧毁了!" + RESET);
        }
        
        System.out.println();
        System.out.println(YELLOW + "最终得分: " + score + RESET);
        System.out.println();
        System.out.println(GREEN + "按 R 重新开始游戏" + RESET);
        System.out.println(RED + "按 ESC 退出游戏" + RESET);
    }
}
