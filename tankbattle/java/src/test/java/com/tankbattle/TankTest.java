package com.tankbattle;

import com.tankbattle.model.Tank;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TankTest {
    
    @Test
    public void testTankCreation() {
        Tank tank = new Tank(5, 5, 'T', "GREEN", true);
        
        assertEquals(5, tank.getX());
        assertEquals(5, tank.getY());
        assertEquals('T', tank.getSymbol());
        assertEquals("GREEN", tank.getColor());
        assertTrue(tank.isPlayer());
        assertEquals(100, tank.getHealth());
        assertTrue(tank.isAlive());
    }
    
    @Test
    public void testTankMove() {
        Tank tank = new Tank(10, 10, 'T', "GREEN", true);
        
        // 测试向右移动
        tank.setDirection(1); // 右
        tank.moveForward(20, 20);
        assertEquals(11, tank.getX());
        assertEquals(10, tank.getY());
        
        // 测试向下移动
        tank.setDirection(2); // 下
        tank.moveForward(20, 20);
        assertEquals(11, tank.getX());
        assertEquals(11, tank.getY());
        
        // 测试边界检查
        tank.setX(19);
        tank.setDirection(1); // 右
        tank.moveForward(20, 20); // 应该不能移动
        assertEquals(19, tank.getX());
    }
    
    @Test
    public void testTankDamage() {
        Tank tank = new Tank(5, 5, 'T', "GREEN", true);
        
        // 受到伤害
        tank.takeDamage(30);
        assertEquals(70, tank.getHealth());
        assertTrue(tank.isAlive());
        
        // 受到致命伤害
        tank.takeDamage(80);
        assertEquals(0, tank.getHealth());
        assertFalse(tank.isAlive());
    }
    
    @Test
    public void testTankShoot() {
        Tank tank = new Tank(5, 5, 'T', "GREEN", true);
        tank.setDirection(1); // 向右
        
        var bullet = tank.shoot();
        assertNotNull(bullet);
        assertEquals(5, bullet.getX());
        assertEquals(5, bullet.getY());
        assertEquals(1, bullet.getDirection());
        assertTrue(bullet.isFromPlayer());
    }
}
