package com.zx.zxutils.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/3/30.
 * 功能：json处理工具
 */
public class ZXJsonUtil {

    /**
     * 获取JSONObject类型
     *
     * @param jsonObject 传入jsonobject
     * @param key        key值
     * @return
     */
    public static JSONObject getJSONObject(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    /**
     * 获取JSONArray类型
     *
     * @param jsonObject 传入jsonobject
     * @param key        key值
     * @return
     */
    public static JSONArray getJSONArray(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getJSONArray(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    /**
     * 获取String类型参数
     *
     * @param jsonObject 传入jsonobject
     * @param key        key值
     * @return
     */
    public static String getStringValue(JSONObject jsonObject, String key) {
        try {
            if (jsonObject.getString(key).equals("null")) {
                return "";
            } else {
                return jsonObject.getString(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取Int类型参数
     *
     * @param jsonObject 传入jsonobject
     * @param key        key值
     * @return
     */
    public static int getIntValue(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取double类型参数
     *
     * @param jsonObject 传入jsonobject
     * @param key        key值
     * @return
     */
    public static double getDoubleValue(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getDouble(key);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取boolean类型参数
     *
     * @param jsonObject 传入jsonobject
     * @param key        key值
     * @return
     */
    public static boolean getBooleanValue(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getBoolean(key);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param json
     * @param classOfT
     * @return
     * @MethodName : fromJson
     * @Description : 用来将JSON串转为对象，但此方法不可用来转带泛型的集合
     */
    public static <T> Object fromJson(String json, Class<T> classOfT) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, (Type) classOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将list转为json字符串
     *
     * @param jsonList
     * @param <T>
     * @return
     */
    public static <T> String tojson(List<T> jsonList) {
        Gson gson = new Gson();
        try {
            return gson.toJson(jsonList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json字符串转换为List
     *
     * @param jsonString
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String jsonString) {
        List<T> datalist = new ArrayList<>();
        Gson gson = new Gson();
        try {
            return datalist = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
