package com.neil.fish.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * @author neil
 * @date 2018/1/9
 */

public class ProgressBarView extends ProgressBar {

    /**
     * View默认的宽
     */
    private static final int DEFAULTWIDTH = 100;

    /**
     * View默认的高度
     */
    private static final int DEFAULTHEIGHT = 100;

    /**
     * 外层圆圈的线条宽度
     */
    private int stoke = 7;

    /**
     * 外层圆圈的线条颜色
     */
    private int circleColor = Color.BLACK;

    /**
     * 内外圆圈之间的间距
     */
    private int paddding = 20;

    /**
     * 内层实体圆的颜色
     */
    private int sweepColor = Color.RED;

    /**
     * 开始绘制的角度
     */
    private int startAngle = -90;

    /**
     * 已经绘制的角度
     */
    private int sweepAngle = 0;

    /**
     * 每次增长的度数
     */
    private int sweepStep = 1;

    /**
     * 画笔
     */
    private Paint paint;

    /**
     * 绘制扇形需要的矩形
     */
    private RectF rectF;

    public ProgressBarView(Context context) {
        this(context, null);
    }

    public ProgressBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        paint.setColor(circleColor); // 设置圆环的颜色
        paint.setStrokeWidth(stoke); // 设置圆环的宽度
        paint.setStyle(Paint.Style.STROKE); // 设置绘制模式
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, (float) (getWidth() / 2 - Math.ceil(stoke / 2.0)), paint);

        paint.setColor(sweepColor); // 设置扇形的颜色
        paint.setStyle(Paint.Style.FILL);
        rectF = new RectF(paddding, paddding, getWidth() - paddding, getHeight() - paddding);
        // 绘制扇形
        canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);


    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec); // 获取宽的测量模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec); // 获取宽的测量值
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); // 获取高的测量模式
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); // 获取高的测量值
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED:
                // 如果宽度为wrap_content,则给定一个默认值
                widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULTWIDTH, getResources().getDisplayMetrics());
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED:
                heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULTHEIGHT, getResources().getDisplayMetrics());
                break;
        }

        widthSize = heightSize = Math.min(widthSize, heightSize);
        setMeasuredDimension(widthSize, heightSize);
    }
}
