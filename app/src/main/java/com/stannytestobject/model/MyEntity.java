package com.stannytestobject.model;

import java.io.File;

/**
 * Created by Xiangb on 2017/8/16.
 * 功能：
 */

public class MyEntity {

    public MyEntity(String name, File file) {
        this.name = name;
        this.file = file;
    }

    private String name;
    private File file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
