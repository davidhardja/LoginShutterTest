package com.example.android.loginshuttertest;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by bocist-8 on 05/10/15.
 */
public class CustomScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;

    private int lastTopValue = 1000;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }

        Rect rect = new Rect();
        View firstChild = this.getChildAt(0);
        ViewGroup groups = (ViewGroup)findViewById(firstChild.getId());

        View backgroundImage = groups.getChildAt(0);
        backgroundImage.getLocalVisibleRect(rect);

        if (lastTopValue != rect.top) {
            lastTopValue = rect.top;
            backgroundImage.setY((float) (rect.top / 2.0));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (scrollViewListener != null) {
            scrollViewListener.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }


}
