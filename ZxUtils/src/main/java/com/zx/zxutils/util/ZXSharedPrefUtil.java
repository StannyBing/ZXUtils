package com.zx.zxutils.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.zx.zxutils.ZXApp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Xiangb on 2017/3/30.
 * 功能：轻量存储的工具类
 */
public class ZXSharedPrefUtil {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context mContext;

    /**
     * 实例化工具
     */
    public ZXSharedPrefUtil() {
        this.mContext = ZXApp.getContext();
        preferences = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 插入任意类型
     *
     * @param key
     * @param value
     */
    public void putValue(String key, Object value) {
        if (value instanceof Integer) {
            putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            putBool(key, (Boolean) value);
        } else if (value instanceof Float) {
            putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            putLong(key, (Long) value);
        } else {
            putString(key, value + "");
        }
    }

    /**
     * 填入String类型参数
     *
     * @param key   key值
     * @param value value值
     */
    public void putString(String key, String value) {
        editor.putString(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 取出String类型参数
     *
     * @param key key值
     * @return
     */
    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defaultVal) {
        return preferences.getString(key, defaultVal);
    }

    /**
     * 填入int类型参数
     *
     * @param key   key值
     * @param value value值
     */
    public void putInt(String key, int value) {
        editor.putInt(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 取出int类型参数
     *
     * @param key key值
     * @return
     */
    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultVal) {
        return preferences.getInt(key, defaultVal);
    }

    /**
     * 填入boolean类型参数
     *
     * @param key   key值
     * @param value value值
     */
    public void putBool(String key, boolean value) {
        editor.putBoolean(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 取出boolean类型参数
     *
     * @param key key值
     * @return
     */
    public boolean getBool(String key) {
        return getBool(key, false);
    }

    public boolean getBool(String key, boolean defaultVal) {
        return preferences.getBoolean(key, defaultVal);
    }

    /**
     * 填入Float类型参数
     *
     * @param key   key值
     * @param value value值
     */
    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 取出Float类型参数
     *
     * @param key key值
     * @return
     */
    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    public float getFloat(String key, float defaultVal) {
        return preferences.getFloat(key, defaultVal);
    }

    /**
     * 填入Long类型参数
     *
     * @param key   key值
     * @param value value值
     */
    public void putLong(String key, long value) {
        editor.putLong(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 取出Long类型参数
     *
     * @param key key值
     * @return
     */
    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long defaultVal) {
        return preferences.getLong(key, defaultVal);
    }

    /**
     * 移除参数
     *
     * @param key key值
     */
    public void remove(String key) {
        if (contains(key)) {
            editor.remove(key);
            SharedPreferencesCompat.apply(editor);
        }
    }

    /**
     * 清空存储
     */
    public void clear() {
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 判断是否存在
     *
     * @param key key值
     * @return
     */
    public boolean contains(String key) {
        return preferences.contains(key);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     * 由于commit是同步存储，异步建议使用apply，但是低版本时，没有apply的方法，所以创建了该兼容类，如果找到了apply就使用apply，没有找到就执行commit方法
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

}
