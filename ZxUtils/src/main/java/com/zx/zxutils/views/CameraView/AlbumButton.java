package com.zx.zxutils.views.CameraView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.zx.zxutils.R;
import com.zx.zxutils.util.ZXBitmapUtil;

/**
 * =====================================
 * 作    者: 陈嘉桐 445263848@qq.com
 * 版    本：1.0.4
 * 创建日期：2017/4/26
 * 描    述：相册按钮
 * =====================================
 */
public class AlbumButton extends View {

    private int size;

    private int center_X;
    private int center_Y;
    private float strokeWidth;

    private Paint paint;
    Path path;
    private Context context;

    public AlbumButton(Context context, int size) {
        this(context);
        this.context = context;
        this.size = size;
        center_X = size / 2;
        center_Y = size / 2;

        strokeWidth = size / 15f;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);

        path = new Path();
    }

    public AlbumButton(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        path.moveTo(strokeWidth, strokeWidth / 2);
//        path.lineTo(center_X, center_Y - strokeWidth / 2);
//        path.lineTo(size - strokeWidth, strokeWidth / 2);
//        canvas.drawPath(path, paint);
        canvas.drawBitmap(ZXBitmapUtil.drawableToBitmap(ContextCompat.getDrawable(context, R.mipmap.camera_album)), strokeWidth, strokeWidth, paint);
    }
}
