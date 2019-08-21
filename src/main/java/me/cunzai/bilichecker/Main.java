package me.cunzai.bilichecker;

import me.cunzai.bilichecker.bstats.Metrics;
import me.cunzai.bilichecker.command.BiliCommand;
import me.cunzai.bilichecker.manager.ConfigManager;
import me.cunzai.bilichecker.manager.RequestManager;
import org.bukkit.Bukkit;
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
        saveDefaultConfig();

        this.configManager = new ConfigManager(this);
        this.request = new RequestManager(this);
        Bukkit.getPluginCommand("BiliChecker").setExecutor(new BiliCommand());

        if (this.getDescription().getAuthors().size() > 1 || !this.getDescription().getAuthors().get(0).equals("存在")) {
            this.setEnabled(false);
            getLogger().info("请不要修改作者名字，这是对作者的一种尊重，谢谢");
            return;
        }
        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));


        getLogger().info("--------------");
        getLogger().info("BiliChecker已加载");
        getLogger().info("作者：存在");
        getLogger().info("--------------");

    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public RequestManager getRequest() {
        return request;
    }
}
