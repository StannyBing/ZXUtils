package com.zx.zxutils.http;

import android.app.Application;
import android.content.Context;

import com.zx.zxutils.http.common.TaskController;

import java.lang.reflect.Method;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;


/**
 * Created by wyouflf on 15/6/10.
 * 任务控制中心, http, image, db, view注入等接口的入口.
 * 需要在在application的onCreate中初始化: x.Ext.init(this);
 */
public final class x {

    private x() {
    }

    public static boolean isDebug() {
        return Ext.debug;
    }

    public static Application app() {
        if (Ext.app == null) {
            try {
                // 在IDE进行布局预览时使用
                Class<?> renderActionClass = Class.forName("com.android.layoutlib.bridge.impl.RenderAction");
                Method method = renderActionClass.getDeclaredMethod("getCurrentContext");
                Context context = (Context) method.invoke(null);
                Ext.app = new MockApplication(context);
            } catch (Throwable ignored) {
                throw new RuntimeException("please confirm your application have extends ZXApplication");
            }
        }
        return Ext.app;
    }

    public static TaskController task() {
        return Ext.taskController;
    }

    public static com.zx.zxutils.http.HttpManager http() {
        if (Ext.httpManager == null) {
            com.zx.zxutils.http.http.HttpManagerImpl.registerInstance();
        }
        return Ext.httpManager;
    }

    public static class Ext {
        private static boolean debug;
        private static Application app;
        private static com.zx.zxutils.http.common.TaskController taskController;
        private static com.zx.zxutils.http.HttpManager httpManager;

        private Ext() {
        }

        public static void init(Application app) {
            com.zx.zxutils.http.common.task.TaskControllerImpl.registerInstance();
            if (Ext.app == null) {
                Ext.app = app;
            }
        }

        public static void setDebug(boolean debug) {
            Ext.debug = debug;
        }

        public static void setTaskController(com.zx.zxutils.http.common.TaskController taskController) {
            if (Ext.taskController == null) {
                Ext.taskController = taskController;
            }
        }

        public static void setHttpManager(com.zx.zxutils.http.HttpManager httpManager) {
            Ext.httpManager = httpManager;
        }

        public static void setDefaultHostnameVerifier(HostnameVerifier hostnameVerifier) {
            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
        }
    }

    private static class MockApplication extends Application {
        public MockApplication(Context baseContext) {
            this.attachBaseContext(baseContext);
        }
    }
}
