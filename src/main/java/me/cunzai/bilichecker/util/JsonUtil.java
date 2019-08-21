package me.cunzai.bilichecker.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class JsonUtil {
    public static JsonObject getJson(URL url){
        try{
            URLConnection urlConnection;
            InputStream inputStream;
            urlConnection = url.openConnection();
            inputStream = urlConnection.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer bs = new StringBuffer();
            String l;
            while ((l = buffer.readLine()) == null) {
                bs.append(l);
            }
            String resp = bs.toString();
            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(resp);
            return json;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
