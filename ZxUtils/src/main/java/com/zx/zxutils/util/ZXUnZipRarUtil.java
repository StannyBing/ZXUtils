package com.zx.zxutils.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.zx.zxutils.forutil.ZXUnZipRarListener;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import java.io.File;
import java.io.FileOutputStream;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.NativeStorage;

/**
 * Created by Xiangb on 2017/7/14.
 * 功能：解压工具
 */

public class ZXUnZipRarUtil {

    private static ZXUnZipRarListener mListener;
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mListener != null) {
                switch (msg.getData().getInt("status")) {
                    case 0://开始
                        mListener.onStart();
                        break;
                    case 1://progress
                        mListener.onPregress(msg.getData().getInt("progress"));
                        break;
                    case 2://成功
                        mListener.onComplete(msg.getData().getString("outputpath"));
                        break;
                    case 3://失败
                        mListener.onError(msg.getData().getString("errormsg"));
                        break;
                    default:
                        break;
                }
            }
        }
    };

    private static void sendHandle(int status, String info) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        switch (status) {
            case 0:
                bundle.putInt("status", 0);
                break;
            case 1:
                bundle.putInt("status", 1);
                bundle.putInt("progress", Integer.parseInt(info));
                break;
            case 2:
                bundle.putInt("status", 2);
                bundle.putString("outputpath", info);
                break;
            case 3:
                bundle.putInt("status", 3);
                bundle.putString("errormsg", info);
                break;

            default:
                break;
        }
        message.setData(bundle);
        handler.sendMessage(message);
    }


    /**
     * 解压zip
     *
     * @param zipFilePath
     * @param outputPath
     * @param listener
     */
    public static void unZip(final String zipFilePath, final String outputPath, final ZXUnZipRarListener listener) {
        mListener = listener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendHandle(0, "");
                    ZipFile zipFile = new ZipFile(zipFilePath);
                    if (!ZXFileUtil.isFileExists(zipFilePath)) {
                        sendHandle(3, "文件不存在");
                        return;
                    }
                    zipFile.setFileNameCharset("GBK");
                    if (!zipFile.isValidZipFile()) {
                        sendHandle(3, "文件不合法");
                        return;
                    }
                    File destDir = new File(outputPath);
                    if (destDir.isDirectory() && !destDir.exists()) {
                        destDir.mkdir();
                    }
                    FileHeader fileHeader = null;
                    final int total = zipFile.getFileHeaders().size();
                    int progressNow = -1;
                    for (int i = 0; i < zipFile.getFileHeaders().size(); i++) {
                        fileHeader = (FileHeader) zipFile.getFileHeaders().get(i);
                        zipFile.extractFile(fileHeader, outputPath);
                        int progress = (int) ((i + 1) * 1.0 * 100 / total);
                        if (progress == progressNow) {//此时与上次相同，就不反复监听了
                            continue;
                        }
                        progressNow = progress;
                        sendHandle(1, progress + "");
                    }
                    sendHandle(2, outputPath);
                } catch (ZipException e) {
                    sendHandle(3, "解压失败，请重试");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 解压rar
     *
     * @param rarFilePath
     * @param outputPath
     * @param listener
     */
    public static void unRar(final String rarFilePath, final String outputPath, final ZXUnZipRarListener listener) {
        mListener = listener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (listener != null) listener.onStart();
                    if (!ZXFileUtil.isFileExists(rarFilePath)) {
                        if (listener != null) listener.onError("文件不存在");
                        return;
                    }
                    Archive rarFile = new Archive(new NativeStorage(new File(rarFilePath)));
                    de.innosystec.unrar.rarfile.FileHeader fileHeader = null;
                    final int total = rarFile.getFileHeaders().size();
                    int progressNow = -1;
                    for (int i = 0; i < rarFile.getFileHeaders().size(); i++) {
                        fileHeader = rarFile.getFileHeaders().get(i);
                        String entrypath = "";
                        if (fileHeader.isUnicode()) {//解决中文乱码
                            entrypath = fileHeader.getFileNameW().trim();
                        } else {
                            entrypath = fileHeader.getFileNameString().trim();
                        }
                        entrypath = entrypath.replaceAll("\\\\", "/");
                        File file = new File(outputPath + entrypath);
                        if (fileHeader.isDirectory()) {
                            file.mkdirs();
                        } else {
                            File parent = file.getParentFile();
                            if (parent != null && !parent.exists()) {
                                parent.mkdirs();
                            }
                            FileOutputStream fileOut = new FileOutputStream(file);
                            rarFile.extractFile(fileHeader, fileOut);
                            fileOut.close();
                            if (listener != null) {
                                int progress = (int) ((i + 1) * 1.0 * 100 / total);
                                if (progress == progressNow) {//此时与上次相同，就不反复监听了
                                    continue;
                                }
                                progressNow = progress;
                                listener.onPregress(progress);
                            }
                        }
                    }
                    if (listener != null) listener.onComplete(outputPath);
                } catch (Exception e) {
                    if (listener != null) listener.onError("解压失败，请重试");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void unFile(final String filePath, final String outputPath, final ZXUnZipRarListener listener) {
        if (filePath.endsWith("rar")) {
            unRar(filePath, outputPath, listener);
        } else if (filePath.endsWith("zip")) {
            unZip(filePath, outputPath, listener);
        } else {
            listener.onError("解压失败，不是有效的解压文件");
        }
    }
}
