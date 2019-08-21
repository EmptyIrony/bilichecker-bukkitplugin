package me.cunzai.bilichecker.manager;

import com.google.gson.internal.$Gson$Preconditions;
import me.cunzai.bilichecker.Main;

import java.util.List;

public class ConfigManager {
    private Main plugin;

    private String version;
    private int fans;
    private int views;

    private String wannaVerify;
    private String verifySetup1;
    private String failedToPass;
    private String success;
    private String repeat;
    private String notInt;
    private List<String> commands;
    private int limit;

    public ConfigManager(Main main){
        this.plugin = main;


    }

    public void loadConfig(){
        this.version = plugin.getConfig().getString("version");

        this.fans = plugin.getConfig().getInt("fans");
        this.views = plugin.getConfig().getInt("views");

        this.wannaVerify = plugin.getConfig().getString("Wanna-Verify");
        this.verifySetup1 = plugin.getConfig().getString("Verifying");
        this.failedToPass = plugin.getConfig().getString("FailedToPass");
        this.success = plugin.getConfig().getString("Success");
        this.repeat = plugin.getConfig().getString("Repeat");
        this.commands = plugin.getConfig().getStringList("commands");
        this.limit = plugin.getConfig().getInt("limit");
        this.notInt = plugin.getConfig().getString("limit");

        plugin.getLogger().info("配置文件读取成功！by BiliChecker");
    }

    public int getFans() {
        return fans;
    }

    public int getViews() {
        return views;
    }

    public String getVerifySetup1() {
        return verifySetup1;
    }

    public String getFailedToPass() {
        return failedToPass;
    }

    public String getSuccess() {
        return success;
    }

    public String getRepeat() {
        return repeat;
    }

    public String getWannaVerify() {
        return wannaVerify;
    }

    public String getVersion() {
        return version;
    }

    public List<String> getCommands() {
        return commands;
    }
}
