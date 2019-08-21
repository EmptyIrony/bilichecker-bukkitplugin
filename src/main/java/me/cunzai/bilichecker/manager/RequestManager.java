package me.cunzai.bilichecker.manager;

import com.google.gson.JsonObject;
import me.cunzai.bilichecker.Main;
import me.cunzai.bilichecker.data.Data;
import me.cunzai.bilichecker.util.JsonUtil;

import java.net.URL;

public class RequestManager {
    private Main plugin;

    public RequestManager(Main main){
        this.plugin = main;
    }

    public String check(final String mid) {

                JsonObject json;
                try {
                    json = JsonUtil.getJson(new URL("http://api.kaaass.net/biliapi/user/space?id=" + mid));
                }catch (Exception e){
                    e.printStackTrace();
                    plugin.getLogger().info("获取粉丝数时异常！请确认插件版本是否是最新版并且服务器网络通畅");
                    return null;
                }
                if (json!=null&&json.get("status").getAsString().equalsIgnoreCase("OK")){
                    if (json.get("data").getAsJsonObject().get("card").getAsJsonObject().get("fans").getAsInt()>= plugin.getConfigManager().getFans()){
                        try{
                            JsonObject jsonObject = JsonUtil.getJson(new URL("http://api.bilibili.com/x/space/upstat?mid="+mid));
                            if (jsonObject!=null&&jsonObject.get("code").getAsInt()==0){
                                if (jsonObject.get("data").getAsJsonObject().get("archive").getAsJsonObject().get("view").getAsInt()>=plugin.getConfigManager().getViews()){
                                    return json.get("data").getAsJsonObject().get("card").getAsJsonObject().get("sign").getAsString();
                                }
                                return null;
                            }else {
                                plugin.getLogger().info("获取播放数时异常！请确认插件版本是否是最新版并且服务器网络通畅");
                                return null;
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            plugin.getLogger().info("获取播放数时异常！请确认插件版本是否是最新版并且服务器网络通畅");
                            return null;
                        }

                    } else return null;
                } else {
                    plugin.getLogger().info("获取粉丝数时异常！请确认插件版本是否是最新版并且服务器网络通畅");
                    return null;
                }
    }

    public boolean checkSign(Data data) {
        JsonObject json;
        try {
            json = JsonUtil.getJson(new URL("http://api.kaaass.net/biliapi/user/space?id=" + data.getMid()));
        } catch (Exception e) {
            e.printStackTrace();
            plugin.getLogger().info("获取签名时异常！请确认插件版本是否是最新版并且服务器网络通畅");
            return false;
        }
        if (json != null && json.get("status").getAsString().equalsIgnoreCase("OK")) {
            return json.get("data").getAsJsonObject().get("card").getAsJsonObject().get("sign").getAsString().equals(Main.getIns().getConfigManager().getVerify() + data.getCode());

        } else {
            plugin.getLogger().info("获取签名时异常！请确认插件版本是否是最新版并且服务器网络通畅");
            return false;
        }
    }
}
