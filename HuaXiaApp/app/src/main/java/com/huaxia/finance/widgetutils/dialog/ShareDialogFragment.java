package com.huaxia.finance.widgetutils.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.huaxia.finance.R;
import com.huaxia.finance.widgetutils.dialog.minterface.OnShareListener;

/**
 * 分享对话框
 * Created by houwen.lai on 2015/12/21.
 */
public class ShareDialogFragment extends DialogFragment{

    private Button btn_weixin_friends;
    private Button btn_weixin_circle;
    private Button btn_weibo;
    private Button btn_qq_frieneds;
    private Button btn_qq_zone;
    private Button btn_share_canael;

    @Override
    public void onStart() {
        super.onStart();
        //去掉这个显示与屏幕边的缝隙
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        //对话框背景色
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black_half)));
        getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );

        //其它
//        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
//////        UserConstant.mhight=wm.getDefaultDisplay().getHeight();//屏幕高度
        final WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width= dm.widthPixels;//wm.getDefaultDisplay().getWidth()
        layoutParams.height = getDialog().getWindow().getAttributes().height;//
        layoutParams.gravity = Gravity.BOTTOM;//
        getDialog().getWindow().setAttributes(layoutParams);

    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().setCanceledOnTouchOutside(true);//可以点击对话框外
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.share_activity, null);

        final Dialog dialog = new Dialog(getActivity(), R.style.ShareDialog);
        dialog.setContentView(view);

        btn_weixin_friends = (Button) view.findViewById(R.id.btn_weixin_friends);
        btn_weixin_circle= (Button) view.findViewById(R.id.btn_weixin_circle);
        btn_weibo= (Button) view.findViewById(R.id.btn_weibo);
        btn_qq_frieneds= (Button) view.findViewById(R.id.btn_qq_frieneds);
        btn_qq_zone= (Button) view.findViewById(R.id.btn_qq_zone);
        btn_share_canael= (Button) view.findViewById(R.id.btn_share_canael);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
//        builder.setView(view);
        // Add action buttons

//        builder.setNeutralButton("2",null);//按钮最左边
        //        builder.setNegativeButton("1",null);//右左按钮
//        builder.setPositiveButton("3",null);//右按钮

        onShareListener = new OnShareClickListener();

        btn_weixin_friends.setOnClickListener(onShareListener);
        btn_weixin_circle.setOnClickListener(onShareListener);
        btn_weibo.setOnClickListener(onShareListener);
        btn_qq_frieneds.setOnClickListener(onShareListener);
        btn_qq_zone.setOnClickListener(onShareListener);

        btn_share_canael.setOnClickListener(onShareListener);

//        return builder.create();
        return dialog;
    }

    private OnShareClickListener onShareListener;
    private class OnShareClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            OnShareListener onShareListener = (OnShareListener)ShareDialogFragment.this.getActivity();
            onShareListener.onShareClick(v);
            dismiss();
        }
    }
}
