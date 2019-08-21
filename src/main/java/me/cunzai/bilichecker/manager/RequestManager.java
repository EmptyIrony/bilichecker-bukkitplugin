package me.cunzai.bilichecker.manager;

import com.google.gson.JsonObject;
import com.google.gson.internal.$Gson$Preconditions;
import me.cunzai.bilichecker.Main;
import me.cunzai.bilichecker.util.JsonUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RequestManager {
    private Main plugin;
    private Map<UUID,String> passed;

    public RequestManager(Main main){
        this.plugin = main;
        this.passed = new HashMap<>();
    }

    public boolean check(final UUID uuid,final String mid){

                JsonObject json;
                try {
                    json = JsonUtil.getJson(new URL("http://api.kaaass.net/biliapi/user/space?id=" + mid));
                }catch (Exception e){
                    e.printStackTrace();
                    plugin.getLogger().info("获取粉丝数时异常！请确认插件版本是否是最新版并且服务器网络通畅");
                    return false;
                }
                if (json!=null&&json.get("status").getAsString().equalsIgnoreCase("OK")){
                    if (json.get("data").getAsJsonObject().get("card").getAsJsonObject().get("fans").getAsInt()>= plugin.getConfigManager().getFans()){
                        try{
                            JsonObject jsonObject = JsonUtil.getJson(new URL("http://api.bilibili.com/x/space/upstat?mid="+mid));
                            if (jsonObject!=null&&jsonObject.get("code").getAsInt()==0){
                                if (jsonObject.get("data").getAsJsonObject().get("archive").getAsJsonObject().get("view").getAsInt()>=plugin.getConfigManager().getViews()){
                                    passed.put(uuid,mid);
                                    return true;
                                }else return false;
                            }else {
                                plugin.getLogger().info("获取播放数时异常！请确认插件版本是否是最新版并且服务器网络通畅");
                                return false;
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            plugin.getLogger().info("获取播放数时异常！请确认插件版本是否是最新版并且服务器网络通畅");
                            return false;
                        }

                    }else return false;
                }else {
                    plugin.getLogger().info("获取粉丝数时异常！请确认插件版本是否是最新版并且服务器网络通畅");
                    return false;
                }
    }

    public Map<UUID, String> getPassed() {
        return passed;
    }
}
