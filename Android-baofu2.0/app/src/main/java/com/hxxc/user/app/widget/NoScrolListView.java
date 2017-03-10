package com.hxxc.user.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/10/26.
 */

public class NoScrolListView extends ListView {
    public NoScrolListView(Context context) {
        super(context);
    }

    public NoScrolListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrolListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
