package com.zx.zxutils.views.BubbleView;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zx.zxutils.R;
import com.zx.zxutils.util.ZXSystemUtil;

/**
 * 气泡布局
 */
public class BubbleRelativeLayout extends RelativeLayout {

    /**
     * 气泡尖角方向
     */
    public enum BubbleLegOrientation {
        TOP, LEFT, RIGHT, BOTTOM, NONE
    }

    public static int PADDING = 30;
    public static int LEG_HALF_BASE = 30;
    public static float STROKE_WIDTH = 2.0f;
    public static float CORNER_RADIUS = 30.0f;
    public static int SHADOW_COLOR = Color.argb(40, 60, 60, 60);
    public static float MIN_LEG_DISTANCE = PADDING + LEG_HALF_BASE;

    private Paint mFillPaint = null;
    private final Path mPath = new Path();
    private final Path mBubbleLegPrototype = new Path();
    private final Paint mPaint = new Paint(Paint.DITHER_FLAG);

    private float mBubbleLegOffset = 1.0f;
    private BubbleLegOrientation mBubbleOrientation = BubbleLegOrientation.LEFT;

    private int bgColor = Color.WHITE;

    public BubbleRelativeLayout(Context context, @ColorRes int bgColor) {
        this(context, null, bgColor);
    }

    public BubbleRelativeLayout(Context context, AttributeSet attrs, @ColorRes int bgColor) {
        super(context, attrs);
        init(context, attrs, bgColor);
    }

    private void init(final Context context, final AttributeSet attrs, @ColorRes int mColor) {

        if (mColor!=0){
            bgColor = ZXSystemUtil.transColor(mColor);
        }

        //setGravity(Gravity.CENTER);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.bubble);

            try {
                PADDING = a.getDimensionPixelSize(R.styleable.bubble_padding, PADDING);
                SHADOW_COLOR = a.getInt(R.styleable.bubble_shadowColor, SHADOW_COLOR);
                LEG_HALF_BASE = a.getDimensionPixelSize(R.styleable.bubble_halfBaseOfLeg, LEG_HALF_BASE);
                MIN_LEG_DISTANCE = PADDING + LEG_HALF_BASE;
                STROKE_WIDTH = a.getFloat(R.styleable.bubble_stroke_width, STROKE_WIDTH);
                CORNER_RADIUS = a.getFloat(R.styleable.bubble_corner_radius, CORNER_RADIUS);
            } finally {
                if (a != null) {
                    a.recycle();
                }
            }
        }

        mPaint.setColor(bgColor);//边线
        mPaint.setStyle(Style.FILL_AND_STROKE);
        mPaint.setStrokeCap(Cap.BUTT);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(STROKE_WIDTH);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setPathEffect(new CornerPathEffect(CORNER_RADIUS));

        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(LAYER_TYPE_SOFTWARE, mPaint);
        }

        mFillPaint = new Paint(mPaint);
        mFillPaint.setColor(Color.WHITE);//这个颜色无用
        mFillPaint.setShader(new LinearGradient(100f, 0f, 100f, 200f, bgColor, bgColor, TileMode.CLAMP));//内部填充的颜色

        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(LAYER_TYPE_SOFTWARE, mFillPaint);
        }
        mPaint.setShadowLayer(3f, 1F, 2F, SHADOW_COLOR);

        renderBubbleLegPrototype();

        setPadding(PADDING, PADDING, PADDING, PADDING);

    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 尖角path
     */
    private void renderBubbleLegPrototype() {
        mBubbleLegPrototype.moveTo(0, 0);
        mBubbleLegPrototype.lineTo(PADDING * 2.5f, -PADDING / 0.8f);//越大越尖
        mBubbleLegPrototype.lineTo(PADDING * 2.5f, PADDING / 0.8f);
        mBubbleLegPrototype.close();
    }

    public void setBubbleParams(final BubbleLegOrientation bubbleOrientation, final float bubbleOffset) {
        mBubbleLegOffset = bubbleOffset;
        mBubbleOrientation = bubbleOrientation;
    }

    public void setBgColor() {

    }

    /**
     * 根据显示方向，获取尖角位置矩阵
     *
     * @param width
     * @param height
     * @return
     */
    private Matrix renderBubbleLegMatrix(final float width, final float height) {

        final float offset = Math.max(mBubbleLegOffset, MIN_LEG_DISTANCE);

        float dstX = 0;
        float dstY = Math.min(offset, height - MIN_LEG_DISTANCE);
        final Matrix matrix = new Matrix();

        switch (mBubbleOrientation) {

            case TOP:
                dstX = Math.min(offset, width - MIN_LEG_DISTANCE);
                dstY = 0;
                matrix.postRotate(90);
                break;

            case RIGHT:
                dstX = width;
                dstY = Math.min(offset, height - MIN_LEG_DISTANCE);
                matrix.postRotate(180);
                break;

            case BOTTOM:
                dstX = Math.min(offset, width - MIN_LEG_DISTANCE);
                dstY = height;
                matrix.postRotate(270);
                break;

        }

        matrix.postTranslate(dstX, dstY);
        return matrix;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        final float width = canvas.getWidth();
        final float height = canvas.getHeight();

//        float[] radiusArray = {CORNER_RADIUS,CORNER_RADIUS,CORNER_RADIUS,CORNER_RADIUS,CORNER_RADIUS,CORNER_RADIUS,CORNER_RADIUS,CORNER_RADIUS};
        float[] radiusArray = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};

        mPath.rewind();
        mPath.addRoundRect(new RectF(PADDING, PADDING, width - PADDING, height - PADDING), radiusArray, Direction.CW);
        mPath.addPath(mBubbleLegPrototype, renderBubbleLegMatrix(width, height));//尖角

        canvas.drawPath(mPath, mPaint);
        canvas.scale((width - STROKE_WIDTH) / width, (height - STROKE_WIDTH) / height, width / 2.5f, height / 2.5f);

        canvas.drawPath(mPath, mFillPaint);
    }
}
