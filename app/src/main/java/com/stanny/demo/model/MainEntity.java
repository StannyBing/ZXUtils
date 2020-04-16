package com.stanny.demo.model;

/**
 * Created by Xiangb on 2018/1/22.
 * 功能：
 */

public class MainEntity {
    private String className;
    private String toolName;
    private Class<?> classFile;
    private int resId = 0;

    public MainEntity(Class<?> classFile, String className, String toolName, int resId) {
        this.className = className;
        this.toolName = toolName;
        this.classFile = classFile;
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public Class<?> getClassFile() {
        return classFile;
    }

    public void setClassFile(Class<?> classFile) {
        this.classFile = classFile;
    }
}
