package com.neil.fish.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.neil.fish.utils.LogUtils;
import com.neil.fish.utils.UIUtils;


/**
 * Created by wangyin on 2017/10/18.
 */

public class CustomSeekBar extends android.support.v7.widget.AppCompatSeekBar {
    /**
     * 文本画笔
     */
    private TextPaint mTextPaint;
    /**
     * 文本
     */
    private String mText;

    private Rect mTextBound;
    private int mWidth;
    private int mHeight;
    public static final int MONEY = 0;
    public static final int WEEK = 1;
    public int type = -1;
    // 设置区间范围
    public int MONEY_MIN = 0;
    public int MONEY_MAX = 0;
    // 标识14天
    public int ONLINE = 0;
    // 设置区间范围
    public int WEEK_MIN = 0;
    public int WEEK_MAX = 0;
    private Context mContext;

    public CustomSeekBar(Context context) {
        this(context, null);
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setOnSeekBarChangeListener(mOnSeekBarChangeListener);

        init();
    }

    private OnSeekBarChangeListener mOnSeekBarChangeListener = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }
    };

    private void init() {
        mTextPaint = new TextPaint();
        mTextPaint = new TextPaint(Paint.FAKE_BOLD_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#333333"));
        mTextPaint.setTextSize(UIUtils.sp2px(14, mContext));
        mTextBound = new Rect();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        if (type == MONEY) {
//            mText = Arithmetic.progressToMoney(getProgress(), MONEY_MIN, MONEY_MAX) + "元";
            mText =  "元";
        } else if (type == WEEK) {
            if (ONLINE == 1 && getProgress() <= 1) {
                mText = WEEK_MIN * 7 + "天";
            } else {
//                mText = Arithmetic.progressToWeek(getProgress(), WEEK_MIN, WEEK_MAX) + "周";
                mText =   "周";
            }
        } else {
            mText = "";
        }
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
        Rect bounds = this.getProgressDrawable().getBounds();
        float xText = bounds.width() * getProgress() / getMax()-mTextBound.width()/2+getPaddingLeft();
        LogUtils.e("xText", xText + "");
        canvas.drawText(mText, xText, mHeight / 2 + mTextBound.height() / 2, mTextPaint);
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getONLINE() {
        return ONLINE;
    }

    public void setONLINE(int ONLINE) {
        this.ONLINE = ONLINE;
    }

    public int getMONEY_MIN() {
        return MONEY_MIN;
    }

    public void setMONEY_MIN(int MONEY_MIN) {
        this.MONEY_MIN = MONEY_MIN;
    }

    public int getMONEY_MAX() {
        return MONEY_MAX;
    }

    public void setMONEY_MAX(int MONEY_MAX) {
        this.MONEY_MAX = MONEY_MAX;
    }

    public int getWEEK_MIN() {
        return WEEK_MIN;
    }

    public void setWEEK_MIN(int WEEK_MIN) {
        this.WEEK_MIN = WEEK_MIN;
    }

    public int getWEEK_MAX() {
        return WEEK_MAX;
    }

    public void setWEEK_MAX(int WEEK_MAX) {
        this.WEEK_MAX = WEEK_MAX;
    }
}
