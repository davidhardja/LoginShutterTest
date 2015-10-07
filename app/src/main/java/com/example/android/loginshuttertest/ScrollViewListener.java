package com.example.android.loginshuttertest;

import android.view.MotionEvent;

/**
 * Created by bocist-8 on 05/10/15.
 */
public interface ScrollViewListener {
    void onScrollChanged(CustomScrollView scrollView,
                         int x, int y, int oldx, int oldy);

    boolean onTouchEvent(MotionEvent event);
}
