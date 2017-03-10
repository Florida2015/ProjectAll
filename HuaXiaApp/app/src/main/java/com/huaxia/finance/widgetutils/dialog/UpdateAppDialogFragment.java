package com.huaxia.finance.widgetutils.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaxia.finance.R;
import com.huaxia.finance.widgetutils.dialog.minterface.OnUpdateAppListener;

/**
 *
 * Created by houwen.lai on 2015/12/18.
 */
public class UpdateAppDialogFragment extends DialogFragment{

    private String detailContent;
    private String positionString;
    private String negativeString;

    public static UpdateAppDialogFragment newInstance(String detail,boolean flag) {
        UpdateAppDialogFragment f = new UpdateAppDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();//detail
        args.putString("detail", detail);//
        args.putBoolean("flag", flag);
        f.setArguments(args);
        return f;
    }

    public static UpdateAppDialogFragment newInstance(String detail,boolean flag,String negativeButton) {
        UpdateAppDialogFragment f = new UpdateAppDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("detail", detail);
        args.putBoolean("flag", flag);
//        args.putString("negative", negativeButton);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailContent = getArguments().getString("detail");
        flag = getArguments().getBoolean("flag");
//        positionString = getArguments().getString("position");
//        negativeString = getArguments().getString("negative");
//        setStyle(DialogFragment.STYLE_NO_FRAME,R.style.UpdateDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
           @Override
           public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
               if (flag&&keyCode == KeyEvent.KEYCODE_BACK) {//KeyEvent.KEYCODE_SEARCH
                   OnUpdateAppListener listener = (OnUpdateAppListener) getActivity();
                   listener.onUpdateApk(11);
                   dismiss();
                   return true; // pretend we've processed it
               }else
                   return false; // pass on to be processed as normal
           }
       });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        //去掉这个显示与屏幕边的缝隙
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
        //对话框背景色
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, getDialog().getWindow().getAttributes().height);

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
////        UserConstant.mhight=wm.getDefaultDisplay().getHeight();//屏幕高度
        final WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
//        layoutParams.width= wm.getDefaultDisplay().getWidth();//
        layoutParams.gravity = Gravity.CENTER;//
        getDialog().getWindow().setAttributes(layoutParams);

        //设置按钮
//        Button positionButton = ((AlertDialog)getDialog()).getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);
//        Button negativeButton = ((AlertDialog)getDialog()).getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE);
//        Button cancelButton = ((AlertDialog)getDialog()).getButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL);
//        positionButton.setTextColor(getActivity().getResources().getColor(R.color.back_half));
//        negativeButton.setTextColor(getActivity().getResources().getColor(R.color.orange_ff92));
//        cancelButton.setTextColor(getActivity().getResources().getColor(R.color.orange_ff92));
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().setCanceledOnTouchOutside(false);//可以点击对话框外
    }

    private TextView tv_content;
    private Button btn_update_fouse;
    private LinearLayout linear_update;
    private Button btn_update_waite;
    private Button btn_update_now;

    private boolean flag = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.update_dialog, null);
//        view.findViewById(R.id.lingear_updat).setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.transparent)));

        final Dialog dialog = new Dialog(getActivity(), R.style.UpdateDialog);
        dialog.setContentView(view);

        tv_content= (TextView) view.findViewById(R.id.tv_update_content);
        btn_update_fouse= (Button) view.findViewById(R.id.btn_update_fouse);
        linear_update= (LinearLayout) view.findViewById(R.id.linear_update);
        btn_update_waite= (Button) view.findViewById(R.id.btn_update_waite);
        btn_update_now= (Button) view.findViewById(R.id.btn_update_now);

        tv_content.setText(detailContent);

        if (flag){
            btn_update_fouse.setVisibility(View.VISIBLE);
            linear_update.setVisibility(View.GONE);
        }else{
            btn_update_fouse.setVisibility(View.GONE);
            linear_update.setVisibility(View.VISIBLE);
        }
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
//        builder.setView(view);
        // Add action buttons


        //强制更新
        btn_update_fouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnUpdateAppListener listener = (OnUpdateAppListener) getActivity();
                listener.onUpdateApk(v.getId());
                dismiss();
            }
        });
        //更新
        btn_update_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnUpdateAppListener listener = (OnUpdateAppListener) getActivity();
                listener.onUpdateApk(v.getId());
                dismiss();
            }
        });
        btn_update_waite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        builder.create()
        return dialog;//
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        OnUpdateAppListener listener = (OnUpdateAppListener) getActivity();
        listener.onCancel();
//        Toast.makeText(getActivity(), "onCancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        OnUpdateAppListener listener = (OnUpdateAppListener) getActivity();
        listener.onDismiss();
//        Toast.makeText(getActivity(), "onDismiss", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OnUpdateAppListener listener = (OnUpdateAppListener) getActivity();
        listener.onDismiss();
    }
}
