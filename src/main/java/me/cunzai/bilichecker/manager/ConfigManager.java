package me.cunzai.bilichecker.manager;

import me.cunzai.bilichecker.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class ConfigManager {
    private Main plugin;

    private String version;
    private int fans;
    private int views;

    private String verifySetup1;
    private String failedToPass;
    private String success;
    private String notInt;
    private List<String> commands;
    private int limit;
    private String inCoolDown;
    private String verify;
    private String verified;
    private String notVerified;

    public ConfigManager(Main main){
        this.plugin = main;


    }

    public boolean isVerified(String mid) {
        File file = new File(plugin.getDataFolder(), "data.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            return false;
        } else {
            List<String> list = (List<String>) config.getList("verified");
            if (list == null) {
                return false;
            }
            return list.contains(mid);
        }

    }

    public void loadConfig(){
        this.version = plugin.getConfig().getString("Version");

        this.fans = plugin.getConfig().getInt("Fans");
        this.views = plugin.getConfig().getInt("Views");

        this.verifySetup1 = plugin.getConfig().getString("Verifying");
        this.failedToPass = plugin.getConfig().getString("FailedToPass");
        this.success = plugin.getConfig().getString("Success");
        this.commands = plugin.getConfig().getStringList("Commands");
        this.limit = plugin.getConfig().getInt("Limit");
        this.notInt = plugin.getConfig().getString("NotInt");
        this.inCoolDown = plugin.getConfig().getString("In-CoolDown");
        this.verify = plugin.getConfig().getString("Verify");
        this.verified = plugin.getConfig().getString("Verified");
        this.notVerified = plugin.getConfig().getString("NotVerified");

        plugin.getLogger().info("配置文件读取成功！by BiliChecker");
    }

    public int getLimit() {
        return limit;
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

    public String getVersion() {
        return version;
    }

    public List<String> getCommands() {
        return commands;
    }

    public String getInCoolDown() {
        return inCoolDown;
    }

    public String getVerify() {
        return verify;
    }

    public String getNotInt() {
        return notInt;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String mid) {
        File file = new File(plugin.getDataFolder(), "data.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            config.set("verified", mid);
        } else {
            List<String> list = (List<String>) config.getList("verified");
            list.add(mid);
            config.set("verified", list);
        }
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
            plugin.getLogger().info("文件保存出现异常");
        }
    }

    public String getNotVerified() {
        return notVerified;
    }
}
