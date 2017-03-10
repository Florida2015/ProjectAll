package com.huaxia.finance.widgetutils.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.huaxia.finance.R;

/**
 * Created by Administrator on 2016/4/11.
 */
public class ContinueRulesDialogFragment extends DialogFragment {

    private ImageButton btn_close_continue;
    private TextView tv_continue_rules;

    private String detail;

    public static ContinueRulesDialogFragment newInstance(String detail) {
        ContinueRulesDialogFragment f = new ContinueRulesDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();//detail
        args.putString("detail", detail);//
        f.setArguments(args);
        return f;
    }

    public static ContinueRulesDialogFragment newInstance(String detail,String negativeButton) {
        ContinueRulesDialogFragment f = new ContinueRulesDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("detail", detail);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detail = getArguments().getString("detail");
//        positionString = getArguments().getString("position");
//        negativeString = getArguments().getString("negative");
//        setStyle(DialogFragment.STYLE_NO_FRAME,R.style.UpdateDialog);
    }

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
        layoutParams.width= dm.widthPixels-120;//wm.getDefaultDisplay().getWidth()
        layoutParams.height = getDialog().getWindow().getAttributes().height;//
        layoutParams.gravity = Gravity.CENTER;//
        getDialog().getWindow().setAttributes(layoutParams);

    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().setCanceledOnTouchOutside(true);//可以点击对话框外
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_continue_rules, null);
        btn_close_continue = (ImageButton) view.findViewById(R.id.btn_close_continue);
        tv_continue_rules= (TextView) view.findViewById(R.id.tv_continue_rules);
        tv_continue_rules.setText(detail);

        builder.setView(view);

        btn_close_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return builder.create();
    }


}
