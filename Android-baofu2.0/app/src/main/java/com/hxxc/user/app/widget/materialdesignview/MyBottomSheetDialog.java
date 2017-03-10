package com.hxxc.user.app.widget.materialdesignview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.ViewGroup;

import com.hxxc.user.app.utils.DisplayUtil;
import com.hxxc.user.app.utils.ImageUtils;

/**
 * Created by chenqun on 2017/1/5.
 */

public class MyBottomSheetDialog extends BottomSheetDialog {
    public MyBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int screenHeight = ImageUtils.getInstance().getmWindowHeight();
        int statusBarHeight = DisplayUtil.getStatusBarHeight(getContext());
        int dialogHeight = screenHeight - statusBarHeight;
        if (null != getWindow())
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
    }
}
