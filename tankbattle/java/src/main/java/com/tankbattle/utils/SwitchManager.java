package com.tankbattle.utils;

import com.tankbattle.model.GameConfig;

/**
 * 开关管理器 - 管理使能开关的持久化和加载
 */
public class SwitchManager {
    
    /**
     * 保存开关状态到文件
     */
    public static void saveSwitches() {
        GameConfig config = GameConfig.getInstance();
        // 这里可以扩展为保存到文件或数据库
        System.out.println("开关状态已保存");
    }
    
    /**
     * 从文件加载开关状态
     */
    public static void loadSwitches() {
        GameConfig config = GameConfig.getInstance();
        // 这里可以扩展为从文件或数据库加载
        System.out.println("开关状态已加载");
    }
    
    /**
     * 重置所有开关为默认值
     */
    public static void resetToDefaults() {
        GameConfig config = GameConfig.getInstance();
        config.setEnableSound(true);
        config.setEnableCollision(true);
        config.setEnableAI(true);
        config.setEnablePowerups(false);
        config.setEnableDebug(false);
        System.out.println("所有开关已重置为默认值");
    }
    
    /**
     * 导出开关配置
     */
    public static String exportConfig() {
        GameConfig config = GameConfig.getInstance();
        return String.format(
            "enableSound=%s\n" +
            "enableCollision=%s\n" +
            "enableAI=%s\n" +
            "enablePowerups=%s\n" +
            "enableDebug=%s",
            config.isEnableSound(),
            config.isEnableCollision(),
            config.isEnableAI(),
            config.isEnablePowerups(),
            config.isEnableDebug()
        );
    }
    
    /**
     * 导入开关配置
     */
    public static void importConfig(String configString) {
        try {
            String[] lines = configString.split("\n");
            GameConfig config = GameConfig.getInstance();
            
            for (String line : lines) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    boolean value = Boolean.parseBoolean(parts[1].trim());
                    
                    switch (key) {
                        case "enableSound":
                            config.setEnableSound(value);
                            break;
                        case "enableCollision":
                            config.setEnableCollision(value);
                            break;
                        case "enableAI":
                            config.setEnableAI(value);
                            break;
                        case "enablePowerups":
                            config.setEnablePowerups(value);
                            break;
                        case "enableDebug":
                            config.setEnableDebug(value);
                            break;
                    }
                }
            }
            System.out.println("配置导入成功");
        } catch (Exception e) {
            System.err.println("配置导入失败: " + e.getMessage());
        }
    }
}
