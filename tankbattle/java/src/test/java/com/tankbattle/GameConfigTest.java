package com.tankbattle;

import com.tankbattle.model.GameConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameConfigTest {
    
    @Test
    public void testSingletonPattern() {
        GameConfig config1 = GameConfig.getInstance();
        GameConfig config2 = GameConfig.getInstance();
        
        assertSame(config1, config2, "应该返回同一个实例");
    }
    
    @Test
    public void testDefaultSwitches() {
        GameConfig config = GameConfig.getInstance();
        
        assertTrue(config.isEnableSound(), "默认音效应该开启");
        assertTrue(config.isEnableCollision(), "默认碰撞检测应该开启");
        assertTrue(config.isEnableAI(), "默认AI应该开启");
        assertFalse(config.isEnablePowerups(), "默认道具系统应该关闭");
        assertFalse(config.isEnableDebug(), "默认调试模式应该关闭");
    }
    
    @Test
    public void testToggleSwitches() {
        GameConfig config = GameConfig.getInstance();
        
        // 保存原始状态
        boolean originalSound = config.isEnableSound();
        
        // 切换开关
        config.toggleSwitch(1); // 切换音效
        assertNotEquals(originalSound, config.isEnableSound(), "切换后状态应该改变");
        
        // 再次切换应该恢复原状
        config.toggleSwitch(1);
        assertEquals(originalSound, config.isEnableSound(), "再次切换应该恢复原状");
    }
    
    @Test
    public void testSwitchStatusString() {
        GameConfig config = GameConfig.getInstance();
        
        String status = config.getSwitchStatus();
        assertNotNull(status);
        assertTrue(status.contains("使能开关状态"));
        assertTrue(status.contains("音效"));
        assertTrue(status.contains("碰撞检测"));
        assertTrue(status.contains("AI"));
        assertTrue(status.contains("道具系统"));
        assertTrue(status.contains("调试模式"));
    }
    
    @Test
    public void testGameParameters() {
        GameConfig config = GameConfig.getInstance();
        
        // 测试默认值
        assertEquals(100, config.getGameSpeed());
        assertEquals(5, config.getMaxBullets());
        assertEquals(40, config.getMapWidth());
        assertEquals(20, config.getMapHeight());
        
        // 测试设置值
        config.setGameSpeed(150);
        config.setMaxBullets(10);
        config.setMapWidth(50);
        config.setMapHeight(30);
        
        assertEquals(150, config.getGameSpeed());
        assertEquals(10, config.getMaxBullets());
        assertEquals(50, config.getMapWidth());
        assertEquals(30, config.getMapHeight());
    }
}
