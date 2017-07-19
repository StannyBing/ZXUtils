package com.zx.zxutils.http.http.request;

import com.zx.zxutils.http.common.util.IOUtil;
import com.zx.zxutils.http.http.RequestParams;
import com.zx.zxutils.http.http.loader.FileLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by wyouflf on 15/11/4.
 * 本地文件请求
 */
public class LocalFileRequest extends UriRequest {

    private InputStream inputStream;

    LocalFileRequest(RequestParams params, Type loadType) throws Throwable {
        super(params, loadType);
    }

    @Override
    public void sendRequest() throws Throwable {

    }

    @Override
    public boolean isLoading() {
        return true;
    }

    @Override
    public String getCacheKey() {
        return null;
    }

    @Override
    public Object loadResult() throws Throwable {
        if (loader instanceof FileLoader) {
            return getFile();
        }
        return this.loader.load(this);
    }

    @Override
    public void clearCacheHeader() {

    }

    private File getFile() {
        String filePath = null;
        if (queryUrl.startsWith("file:")) {
            filePath = queryUrl.substring("file:".length());
        } else {
            filePath = queryUrl;
        }
        // filePath开始位置多余的"/"或被自动去掉
        return new File(filePath);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (inputStream == null) {
            inputStream = new FileInputStream(getFile());
        }
        return inputStream;
    }

    @Override
    public void close() throws IOException {
        IOUtil.closeQuietly(inputStream);
        inputStream = null;
    }

    @Override
    public long getContentLength() {
        return getFile().length();
    }

    @Override
    public int getResponseCode() throws IOException {
        return getFile().exists() ? 200 : 404;
    }

    @Override
    public String getResponseMessage() throws IOException {
        return null;
    }

    @Override
    public long getExpiration() {
        return -1;
    }

    @Override
    public long getLastModified() {
        return getFile().lastModified();
    }

    @Override
    public String getETag() {
        return null;
    }

    @Override
    public String getResponseHeader(String name) {
        return null;
    }

    @Override
    public Map<String, List<String>> getResponseHeaders() {
        return null;
    }

    @Override
    public long getHeaderFieldDate(String name, long defaultValue) {
        return defaultValue;
    }
}
