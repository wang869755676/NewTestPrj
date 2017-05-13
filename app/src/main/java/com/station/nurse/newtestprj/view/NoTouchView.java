package com.station.nurse.newtestprj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jin on 2017/5/13.
 */

public class NoTouchView extends View {
    public NoTouchView(Context context) {
        super(context);
    }

    public NoTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
