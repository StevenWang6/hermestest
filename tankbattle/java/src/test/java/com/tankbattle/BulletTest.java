package com.tankbattle;

import com.tankbattle.model.Bullet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BulletTest {
    
    @Test
    public void testBulletCreation() {
        Bullet bullet = new Bullet(10, 10, 1, true);
        
        assertEquals(10, bullet.getX());
        assertEquals(10, bullet.getY());
        assertEquals(1, bullet.getDirection());
        assertTrue(bullet.isActive());
        assertTrue(bullet.isFromPlayer());
    }
    
    @Test
    public void testBulletMove() {
        // 测试向右移动
        Bullet bullet1 = new Bullet(10, 10, 1, true);
        bullet1.move();
        assertEquals(11, bullet1.getX());
        assertEquals(10, bullet1.getY());
        
        // 测试向下移动
        Bullet bullet2 = new Bullet(10, 10, 2, true);
        bullet2.move();
        assertEquals(10, bullet2.getX());
        assertEquals(11, bullet2.getY());
        
        // 测试向左移动
        Bullet bullet3 = new Bullet(10, 10, 3, true);
        bullet3.move();
        assertEquals(9, bullet3.getX());
        assertEquals(10, bullet3.getY());
        
        // 测试向上移动
        Bullet bullet4 = new Bullet(10, 10, 0, true);
        bullet4.move();
        assertEquals(10, bullet4.getX());
        assertEquals(9, bullet4.getY());
    }
    
    @Test
    public void testOutOfBounds() {
        // 测试边界检查
        Bullet bullet = new Bullet(0, 0, 3, true); // 向左移动
        
        assertTrue(bullet.isOutOfBounds(10, 10), "(-1,0) 应该在边界外");
        
        bullet.setX(9);
        bullet.setY(9);
        bullet.setDirection(1); // 向右移动
        bullet.move();
        
        assertTrue(bullet.isOutOfBounds(10, 10), "(10,9) 应该在边界外");
    }
    
    @Test
    public void testCollisionDetection() {
        Bullet bullet = new Bullet(5, 5, 1, true);
        
        // 测试碰撞检测
        assertTrue(bullet.checkCollision(5, 5), "相同位置应该检测到碰撞");
        assertFalse(bullet.checkCollision(6, 5), "不同位置应该检测不到碰撞");
        assertFalse(bullet.checkCollision(5, 6), "不同位置应该检测不到碰撞");
    }
    
    @Test
    public void testInactiveBullet() {
        Bullet bullet = new Bullet(10, 10, 1, true);
        bullet.setActive(false);
        
        // 不活跃的炮弹不应该移动
        int originalX = bullet.getX();
        int originalY = bullet.getY();
        bullet.move();
        
        assertEquals(originalX, bullet.getX());
        assertEquals(originalY, bullet.getY());
    }
}
