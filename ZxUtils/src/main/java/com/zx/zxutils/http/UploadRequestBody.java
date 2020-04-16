package com.zx.zxutils.http;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by Xiangb on 2018/6/27.
 * 功能：
 */
public class UploadRequestBody extends RequestBody {
    protected RequestBody requestBody;
    private UploadListener listener;
    //    private CountingSink countingSink;
    private BufferedSink bufferedSink;

    public UploadRequestBody(RequestBody requestBody, UploadListener listener) {
        this.requestBody = requestBody;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

//    @Override
//    public void writeTo(BufferedSink sink) throws IOException {
//        countingSink = new CountingSink(sink);
//        if (bufferedSink == null)
//            bufferedSink = Okio.buffer(countingSink);
//        requestBody.writeTo(bufferedSink);
//        bufferedSink.flush();
//    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
//        countingSink = new CountingSink(sink);
        if (sink instanceof Buffer) {
            requestBody.writeTo(sink);
            return;
        }
        if (bufferedSink == null)
            bufferedSink = Okio.buffer(sink(sink));
        //写入
        requestBody.writeTo(bufferedSink);
        //必须调bubufferedSink.flush();不然最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            long byteWritten = 0L;//当前写入字节数
            long contentLength = 0L;//总字节长度，避免多次调用contentLength()方法

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0)
                    contentLength = contentLength();//获取总字节长度，避免多次调用
                byteWritten += byteCount;//累计当前写入字节数
                if (listener != null) {
                    listener.onRequestProgress((int) (byteWritten * 100.f / contentLength), byteWritten == contentLength);
                }
            }
        };
    }

//    protected final class CountingSink extends ForwardingSink {
//        long byteWriten = 0L;
//        Long contentLength = 0L;
//
//        public CountingSink(Sink delegate) {
//            super(delegate);
//        }
//
//        @Override
//        public void write(Buffer source, long byteCount) throws IOException {
//            super.write(source, byteCount);
//            byteWriten += byteCount;
//            if (contentLength == 0)
//                contentLength = contentLength();
//            listener.onRequestProgress(byteWriten, contentLength);
//        }
//    }

    public interface UploadListener {
        void onRequestProgress(int progress, boolean done);
    }
}
