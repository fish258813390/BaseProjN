package com.neil.fish.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.neil.fish.utils.LogUtils;

import java.util.Calendar;

/**
 * Clock
 *
 * @author neil
 * @date 2018/1/9
 */
public class ClockView extends View {

    /**
     * 绘制表盘的画笔
     */
    private Paint circlePaint;

    /**
     * 绘制表盘数字
     */
    private Paint numPaint;

    /**
     * 绘制表心
     */
    private Paint dotPaint;

    /**
     * 时针
     */
    private Paint hourPaint;

    /**
     * 分针
     */
    private Paint minutePaint;

    /**
     * 秒针
     */
    private Paint secondPaint;

    /**
     * View 宽度 默认 256dp
     */
    private int width;

    /**
     * View高度  默认 256dp
     */
    private int height;

    /**
     * 日历类,获取当前时间
     */
    private Calendar calendar;

    /**
     * 当前时针颜色
     */
    private int hourColor;

    /**
     * 当前分针颜色
     */
    private int minuteColor;

    /**
     * 当前秒针颜色
     */
    private int secondColor;

    /**
     * 时针宽度
     */
    private int hourWidth;

    /**
     * 分针宽度
     */
    private int minuteWidth;

    /**
     * 秒针宽度
     */
    private int secondWidth;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        calendar = Calendar.getInstance();
        // 时钟默认宽高
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 256, getResources().getDisplayMetrics());
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 256, getResources().getDisplayMetrics());
        // 初始化表针的颜色
        hourColor = Color.RED;
        minuteColor = Color.GREEN;
        secondColor = Color.BLUE;
        // 初始化表针宽度
        hourWidth = 8;
        minuteWidth = 5;
        secondWidth = 2;

        /**********************初始化画笔 ********************/
        // 绘制表盘的画笔
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true); //去锯齿
        circlePaint.setColor(Color.GREEN); //设置画笔颜色
        circlePaint.setStyle(Paint.Style.STROKE); //设置画笔style为描边
        circlePaint.setStrokeWidth(6); //设置描边的宽度

        // 绘制表心
        dotPaint = new Paint();
        dotPaint.setAntiAlias(true);
        dotPaint.setColor(Color.RED);
        dotPaint.setStyle(Paint.Style.FILL);

        // 表盘数字
        numPaint = new Paint();
        numPaint.setAntiAlias(true);
        numPaint.setColor(Color.RED);
        numPaint.setTextAlign(Paint.Align.CENTER); // 文本对其方式
        numPaint.setTextSize(35);

        // 时针
        hourPaint = new Paint();
        hourPaint.setColor(hourColor);
        hourPaint.setStyle(Paint.Style.FILL);
        hourPaint.setStrokeWidth(hourWidth);

        // 分针
        minutePaint = new Paint();
        minutePaint.setColor(minuteColor);
        minutePaint.setStyle(Paint.Style.FILL);
        minutePaint.setStrokeWidth(minuteWidth);

        // 秒针
        secondPaint = new Paint();
        secondPaint.setColor(secondColor);
        secondPaint.setStyle(Paint.Style.FILL);
        minutePaint.setStrokeWidth(secondWidth);

        // 绘制钟表

    }

    // 绘制View
    @Override
    protected void onDraw(Canvas canvas) {
        calendar = Calendar.getInstance();
//        int middleWidth = width / 2;
//        int middleHeight = height / 2;
        int radius = width / 2 - 10; // 半径
        // 画表盘 参数一 圆心X轴坐标;参数二 圆心Y轴坐标; 参数三 半径; 参数4 画笔
        canvas.drawCircle(width / 2, height / 2, radius, circlePaint);

        // 画表心
        canvas.drawCircle(width / 2, height / 2, 15, dotPaint);

        // 刻度
        for (int i = 1; i < 13; i++) {
            canvas.save(); // 在旋转之前先保存画布状态
            canvas.rotate(i * 30, width / 2, height / 2); // 旋转画布 角度为正值:顺时针旋转,负值逆时针旋转

            // 1,2 起点坐标; 3,4终点坐标; 5.画笔
            // 画线
            canvas.drawLine(width / 2, height / 2 - radius, width / 2, height / 2 - radius + 20, circlePaint);

            // 画表盘数字 参数一 1.要绘制的文本 ;参数二 文本X轴坐标; 参数三 文本基线; 参数四 文本画笔
            canvas.drawText(i + "", width / 2, height / 2 - radius + 52, numPaint);
//            LogUtils.d("绘制信息：---->" + "刻度:"+ i+",X轴:" + width / 2 +",Y轴:" + (height / 2 - radius + 62)  );
            // 恢复画布状态
            canvas.restore();
        }

        int hour = calendar.get(Calendar.HOUR);// 当前小时数
        LogUtils.d("当前时间：---->" + calendar.get(Calendar.HOUR) + "," + calendar.get(Calendar.MINUTE) + "," + calendar.get(Calendar.SECOND));
        canvas.save();
        // 旋转屏幕
        canvas.rotate(hour * 30, width / 2, height / 2);
        // 画时针
//        canvas.drawLine(width / 2, height / 2 + 20, width / 2, height / 2 - 90, hourPaint);
        canvas.drawLine(width / 2, height / 2 , width / 2,height / 2 - radius + 140 , hourPaint);
//        canvas.drawText("我",width / 2, height / 2, numPaint);
        canvas.restore();
        int minute = calendar.get(Calendar.MINUTE); // 当前分钟
        canvas.save();
        //
        canvas.rotate(minute * 6, width / 2,  height / 2);
        canvas.drawLine(width / 2,  height / 2 + 30, width / 2,  height / 2 - radius + 100, minutePaint);
        canvas.restore();

        int second = calendar.get(Calendar.SECOND); // 当前秒数
        canvas.save();
        canvas.rotate(second * 6, width / 2,  height / 2);
        canvas.drawLine(width / 2,  height / 2 + 40, width / 2,  height / 2 - radius + 40 , secondPaint);
        canvas.restore();

        // 每隔一秒钟 重绘,重绘会调用onDraw() 方法
        postInvalidateDelayed(1000);
    }


}
