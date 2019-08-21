package me.cunzai.bilichecker;

import me.cunzai.bilichecker.manager.ConfigManager;
import me.cunzai.bilichecker.manager.RequestManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main ins;
    private ConfigManager configManager;
    private RequestManager request;

    public static Main getIns() {
        return ins;
    }


    @Override
    public void onEnable() {
        ins = this;
        this.configManager = new ConfigManager(this);
        this.request = new RequestManager(this);

    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public RequestManager getRequest() {
        return request;
    }
}
