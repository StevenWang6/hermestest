package com.tankbattle.controller;

import com.tankbattle.model.*;
import com.tankbattle.view.ConsoleRenderer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 游戏控制器 - 核心游戏逻辑
 */
public class GameController {
    private Tank playerTank;
    private Tank enemyTank;
    private List<Bullet> bullets;
    private char[][] map;
    private int score;
    private boolean gameRunning;
    private boolean gamePaused;
    private boolean inSwitchMenu;
    
    private ConsoleRenderer renderer;
    private InputHandler inputHandler;
    private GameConfig config;
    private Random random;
    
    public GameController() {
        config = GameConfig.getInstance();
        renderer = new ConsoleRenderer();
        inputHandler = new InputHandler();
        random = new Random();
        bullets = new ArrayList<>();
        
        initializeGame();
    }
    
    /**
     * 初始化游戏
     */
    private void initializeGame() {
        // 创建地图
        int width = config.getMapWidth();
        int height = config.getMapHeight();
        map = new char[width][height];
        
        // 初始化地图（空地）
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = ' ';
            }
        }
        
        // 添加障碍物
        addObstacles();
        
        // 创建玩家坦克（左下角）
        playerTank = new Tank(2, height - 3, '▲', "GREEN", true);
        
        // 创建敌人坦克（右上角）
        enemyTank = new Tank(width - 3, 2, '▼', "RED", false);
        
        score = 0;
        gameRunning = true;
        gamePaused = false;
        inSwitchMenu = false;
    }
    
    /**
     * 添加障碍物
     */
    private void addObstacles() {
        int width = map.length;
        int height = map[0].length;
        
        // 添加边界墙
        for (int x = 0; x < width; x++) {
            map[x][0] = '#';
            map[x][height - 1] = '#';
        }
        for (int y = 0; y < height; y++) {
            map[0][y] = '#';
            map[width - 1][y] = '#';
        }
        
        // 添加随机障碍物
        int obstacleCount = (width * height) / 20; // 5%的障碍物
        for (int i = 0; i < obstacleCount; i++) {
            int x = random.nextInt(width - 4) + 2;
            int y = random.nextInt(height - 4) + 2;
            map[x][y] = '#';
        }
    }
    
    /**
     * 开始游戏主循环
     */
    public void startGame() {
        renderer.renderStartScreen();
        inputHandler.waitForKey(); // 等待开始
        
        while (gameRunning) {
            if (!gamePaused && !inSwitchMenu) {
                updateGame();
            }
            
            handleInput();
            renderGame();
            
            try {
                Thread.sleep(config.getGameSpeed());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        inputHandler.close();
    }
    
    /**
     * 更新游戏状态
     */
    private void updateGame() {
        // 更新炮弹
        updateBullets();
        
        // 更新敌人AI
        if (config.isEnableAI() && enemyTank.isAlive()) {
            updateEnemyAI();
        }
        
        // 检查游戏结束条件
        checkGameOver();
    }
    
    /**
     * 更新炮弹状态
     */
    private void updateBullets() {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        
        for (Bullet bullet : bullets) {
            if (!bullet.isActive()) {
                bulletsToRemove.add(bullet);
                continue;
            }
            
            // 移动炮弹
            bullet.move();
            
            // 检查边界
            if (bullet.isOutOfBounds(map.length, map[0].length)) {
                bullet.setActive(false);
                bulletsToRemove.add(bullet);
                continue;
            }
            
            // 检查碰撞（如果碰撞检测开启）
            if (config.isEnableCollision()) {
                checkBulletCollision(bullet);
            }
        }
        
        // 移除无效的炮弹
        bullets.removeAll(bulletsToRemove);
    }
    
    /**
     * 检查炮弹碰撞
     */
    private void checkBulletCollision(Bullet bullet) {
        // 检查与玩家坦克的碰撞
        if (bullet.isFromPlayer() && enemyTank.isAlive() && 
            bullet.checkCollision(enemyTank.getX(), enemyTank.getY())) {
            enemyTank.takeDamage(25);
            bullet.setActive(false);
            if (!enemyTank.isAlive()) {
                score += 100;
            }
            return;
        }
        
        // 检查与敌人坦克的碰撞
        if (!bullet.isFromPlayer() && playerTank.isAlive() && 
            bullet.checkCollision(playerTank.getX(), playerTank.getY())) {
            playerTank.takeDamage(25);
            bullet.setActive(false);
            return;
        }
        
        // 检查与障碍物的碰撞
        int x = bullet.getX();
        int y = bullet.getY();
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length) {
            if (map[x][y] == '#') {
                // 有概率破坏障碍物
                if (random.nextInt(100) < 30) { // 30%概率破坏
                    map[x][y] = ' ';
                }
                bullet.setActive(false);
            }
        }
    }
    
    /**
     * 更新敌人AI
     */
    private void updateEnemyAI() {
        // 简单AI：随机移动和射击
        int action = random.nextInt(100);
        
        if (action < 40) { // 40%概率移动
            int direction = random.nextInt(4);
            enemyTank.setDirection(direction);
            enemyTank.moveForward(map.length, map[0].length);
        } else if (action < 60) { // 20%概率射击
            if (bullets.size() < config.getMaxBullets()) {
                bullets.add(enemyTank.shoot());
            }
        }
        // 其他情况：保持不动
    }
    
    /**
     * 处理输入
     */
    private void handleInput() {
        char input = inputHandler.getCharInput();
        
        if (inSwitchMenu) {
            handleSwitchMenuInput(input);
        } else if (gamePaused) {
            handlePauseMenuInput(input);
        } else {
            handleGameInput(input);
        }
    }
    
    /**
     * 处理游戏中的输入
     */
    private void handleGameInput(char input) {
        switch (Character.toLowerCase(input)) {
            case 'w':
                playerTank.setDirection(0);
                playerTank.moveForward(map.length, map[0].length);
                break;
            case 'd':
                playerTank.setDirection(1);
                playerTank.moveForward(map.length, map[0].length);
                break;
            case 's':
                playerTank.setDirection(2);
                playerTank.moveForward(map.length, map[0].length);
                break;
            case 'a':
                playerTank.setDirection(3);
                playerTank.moveForward(map.length, map[0].length);
                break;
            case ' ': // 空格键发射
                if (bullets.size() < config.getMaxBullets()) {
                    bullets.add(playerTank.shoot());
                }
                break;
            case 'p':
                gamePaused = true;
                break;
            case 't':
                inSwitchMenu = true;
                break;
            case 27: // ESC键
                gameRunning = false;
                break;
        }
    }
    
    /**
     * 处理暂停菜单输入
     */
    private void handlePauseMenuInput(char input) {
        switch (Character.toLowerCase(input)) {
            case 'p':
                gamePaused = false;
                break;
            case 't':
                inSwitchMenu = true;
                gamePaused = false;
                break;
            case 27: // ESC键
                gameRunning = false;
                break;
        }
    }
    
    /**
     * 处理开关菜单输入
     */
    private void handleSwitchMenuInput(char input) {
        if (input >= '1' && input <= '5') {
            int switchIndex = Character.getNumericValue(input);
            config.toggleSwitch(switchIndex);
            renderer.renderSwitchMenu();
        } else if (input == '\n' || input == '\r') { // Enter键
            inSwitchMenu = false;
        } else if (input == 27) { // ESC键
            inSwitchMenu = false;
        }
    }
    
    /**
     * 渲染游戏
     */
    private void renderGame() {
        if (inSwitchMenu) {
            renderer.renderSwitchMenu();
        } else if (gamePaused) {
            renderer.renderGame(playerTank, enemyTank, bullets, map, score, false);
            renderer.renderPauseMenu();
        } else if (!playerTank.isAlive() || !enemyTank.isAlive()) {
            renderer.renderGameOverScreen(score, !playerTank.isAlive() ? false : true);
        } else {
            renderer.renderGame(playerTank, enemyTank, bullets, map, score, false);
        }
    }
    
    /**
     * 检查游戏结束条件
     */
    private void checkGameOver() {
        if (!playerTank.isAlive() || !enemyTank.isAlive()) {
            // 游戏结束，等待重新开始或退出
            char input = inputHandler.waitForKey();
            if (Character.toLowerCase(input) == 'r') {
                // 重新开始游戏
                initializeGame();
            } else if (input == 27) {
                // 退出游戏
                gameRunning = false;
            }
        }
    }
    
    /**
     * 获取游戏状态
     */
    public String getGameStatus() {
        return String.format(
            "游戏状态: %s | 分数: %d | 玩家HP: %d | 敌人HP: %d | 炮弹数: %d",
            gameRunning ? "运行中" : "已结束",
            score,
            playerTank.getHealth(),
            enemyTank.getHealth(),
            bullets.size()
        );
    }
}
