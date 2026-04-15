package com.tankbattle.model;

/**
 * 坦克类
 */
public class Tank {
    private int x;          // X坐标
    private int y;          // Y坐标
    private int health;     // 生命值
    private int direction;  // 方向：0=上, 1=右, 2=下, 3=左
    private char symbol;    // 显示符号
    private String color;   // 颜色（控制台）
    private boolean isPlayer; // 是否是玩家
    
    public Tank(int x, int y, char symbol, String color, boolean isPlayer) {
        this.x = x;
        this.y = y;
        this.health = 100;
        this.direction = 1; // 默认向右
        this.symbol = symbol;
        this.color = color;
        this.isPlayer = isPlayer;
    }
    
    // 移动方法
    public void move(int dx, int dy, int mapWidth, int mapHeight) {
        int newX = x + dx;
        int newY = y + dy;
        
        // 边界检查
        if (newX >= 0 && newX < mapWidth && newY >= 0 && newY < mapHeight) {
            x = newX;
            y = newY;
        }
    }
    
    // 根据方向移动
    public void moveForward(int mapWidth, int mapHeight) {
        switch (direction) {
            case 0: // 上
                move(0, -1, mapWidth, mapHeight);
                break;
            case 1: // 右
                move(1, 0, mapWidth, mapHeight);
                break;
            case 2: // 下
                move(0, 1, mapWidth, mapHeight);
                break;
            case 3: // 左
                move(-1, 0, mapWidth, mapHeight);
                break;
        }
    }
    
    // 发射炮弹
    public Bullet shoot() {
        return new Bullet(x, y, direction, isPlayer);
    }
    
    // 受到伤害
    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }
    
    // 是否存活
    public boolean isAlive() {
        return health > 0;
    }
    
    // Getters and Setters
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    
    public int getDirection() { return direction; }
    public void setDirection(int direction) { this.direction = direction; }
    
    public char getSymbol() { return symbol; }
    public void setSymbol(char symbol) { this.symbol = symbol; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public boolean isPlayer() { return isPlayer; }
    public void setPlayer(boolean player) { isPlayer = player; }
    
    @Override
    public String toString() {
        return String.format("Tank[%c] at (%d,%d) HP:%d", symbol, x, y, health);
    }
}
