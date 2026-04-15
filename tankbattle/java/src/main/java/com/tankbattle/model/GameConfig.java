package com.tankbattle.model;

/**
 * 游戏配置类 - 包含使能开关系统
 */
public class GameConfig {
    // 使能开关定义
    private boolean enableSound = true;
    private boolean enableCollision = true;
    private boolean enableAI = true;
    private boolean enablePowerups = false;
    private boolean enableDebug = false;
    
    // 游戏参数
    private int gameSpeed = 100; // 游戏速度（毫秒）
    private int maxBullets = 5;  // 最大炮弹数
    private int mapWidth = 40;   // 地图宽度
    private int mapHeight = 20;  // 地图高度
    
    // 单例模式
    private static GameConfig instance;
    
    private GameConfig() {
        // 私有构造函数
    }
    
    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }
    
    // 使能开关的getter和setter方法
    public boolean isEnableSound() {
        return enableSound;
    }
    
    public void setEnableSound(boolean enableSound) {
        this.enableSound = enableSound;
        System.out.println("音效开关: " + (enableSound ? "开启" : "关闭"));
    }
    
    public boolean isEnableCollision() {
        return enableCollision;
    }
    
    public void setEnableCollision(boolean enableCollision) {
        this.enableCollision = enableCollision;
        System.out.println("碰撞检测开关: " + (enableCollision ? "开启" : "关闭"));
    }
    
    public boolean isEnableAI() {
        return enableAI;
    }
    
    public void setEnableAI(boolean enableAI) {
        this.enableAI = enableAI;
        System.out.println("AI开关: " + (enableAI ? "开启" : "关闭"));
    }
    
    public boolean isEnablePowerups() {
        return enablePowerups;
    }
    
    public void setEnablePowerups(boolean enablePowerups) {
        this.enablePowerups = enablePowerups;
        System.out.println("道具系统开关: " + (enablePowerups ? "开启" : "关闭"));
    }
    
    public boolean isEnableDebug() {
        return enableDebug;
    }
    
    public void setEnableDebug(boolean enableDebug) {
        this.enableDebug = enableDebug;
        System.out.println("调试模式开关: " + (enableDebug ? "开启" : "关闭"));
    }
    
    // 游戏参数的getter和setter方法
    public int getGameSpeed() {
        return gameSpeed;
    }
    
    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
    }
    
    public int getMaxBullets() {
        return maxBullets;
    }
    
    public void setMaxBullets(int maxBullets) {
        this.maxBullets = maxBullets;
    }
    
    public int getMapWidth() {
        return mapWidth;
    }
    
    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }
    
    public int getMapHeight() {
        return mapHeight;
    }
    
    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }
    
    /**
     * 获取所有开关状态的字符串表示
     */
    public String getSwitchStatus() {
        return String.format(
            "使能开关状态:\n" +
            "  1. 音效: %s\n" +
            "  2. 碰撞检测: %s\n" +
            "  3. AI: %s\n" +
            "  4. 道具系统: %s\n" +
            "  5. 调试模式: %s",
            enableSound ? "开启" : "关闭",
            enableCollision ? "开启" : "关闭",
            enableAI ? "开启" : "关闭",
            enablePowerups ? "开启" : "关闭",
            enableDebug ? "开启" : "关闭"
        );
    }
    
    /**
     * 切换指定开关的状态
     * @param switchIndex 开关索引（1-5）
     */
    public void toggleSwitch(int switchIndex) {
        switch (switchIndex) {
            case 1:
                setEnableSound(!enableSound);
                break;
            case 2:
                setEnableCollision(!enableCollision);
                break;
            case 3:
                setEnableAI(!enableAI);
                break;
            case 4:
                setEnablePowerups(!enablePowerups);
                break;
            case 5:
                setEnableDebug(!enableDebug);
                break;
            default:
                System.out.println("无效的开关索引: " + switchIndex);
        }
    }
}
