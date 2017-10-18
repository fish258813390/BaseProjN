package com.neil.fish.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wy on 11/4/16.
 * 不能滑动的ViewPager
 */

public class NoScrollViewPager extends NoPreloadViewPager {

  private boolean isPagingEnabled = false;
  public NoScrollViewPager(Context context) {
    super(context);
  }

  public NoScrollViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return this.isPagingEnabled && super.onTouchEvent(event);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    return this.isPagingEnabled && super.onInterceptTouchEvent(event);
  }

  @Override public void scrollTo(int x, int y) {
    super.scrollTo(x, y);
  }
}
