package me.cunzai.bilichecker.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Data {
    public static Map<UUID, Data> dataMap = new HashMap<>();

    private UUID uuid;
    private String mid;
    private long lastTime;
    private String code;
    private String sign;

    public Data(UUID uuid, String mid, long time, String code, String sign) {
        this.uuid = uuid;
        this.mid = mid;
        this.lastTime = time;
        this.code = code;
        this.sign = sign;

    }

    public String getSign() {
        return sign;
    }

    public long getLastTime() {
        return lastTime;
    }

    public String getMid() {
        return mid;
    }

    public String getCode() {
        return code;
    }
}
