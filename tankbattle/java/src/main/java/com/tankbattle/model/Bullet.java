package com.tankbattle.model;

/**
 * 炮弹类
 */
public class Bullet {
    private int x;          // X坐标
    private int y;          // Y坐标
    private int direction;  // 方向
    private boolean active; // 是否激活
    private boolean fromPlayer; // 是否来自玩家
    
    public Bullet(int x, int y, int direction, boolean fromPlayer) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.active = true;
        this.fromPlayer = fromPlayer;
    }
    
    // 移动炮弹
    public void move() {
        if (!active) return;
        
        switch (direction) {
            case 0: // 上
                y--;
                break;
            case 1: // 右
                x++;
                break;
            case 2: // 下
                y++;
                break;
            case 3: // 左
                x--;
                break;
        }
    }
    
    // 检查是否超出边界
    public boolean isOutOfBounds(int mapWidth, int mapHeight) {
        return x < 0 || x >= mapWidth || y < 0 || y >= mapHeight;
    }
    
    // 检查碰撞
    public boolean checkCollision(int targetX, int targetY) {
        return x == targetX && y == targetY;
    }
    
    // Getters and Setters
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    
    public int getDirection() { return direction; }
    public void setDirection(int direction) { this.direction = direction; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public boolean isFromPlayer() { return fromPlayer; }
    public void setFromPlayer(boolean fromPlayer) { this.fromPlayer = fromPlayer; }
    
    @Override
    public String toString() {
        return String.format("Bullet at (%d,%d) dir:%d active:%s", 
            x, y, direction, active ? "Y" : "N");
    }
}
