package com.hxxc.huaxing.app.ui.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ImageButton;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.ui.dialogfragment.dialoglistener.OnOpenEListener;
import com.trello.rxlifecycle.components.support.RxDialogFragment;

/**
 * Created by Administrator on 2016/9/28.
 * 我的 开通e账号 对话框
 */

public class DialogMineOpenEFragment extends RxDialogFragment {


    public static DialogMineOpenEFragment newInstance() {
        DialogMineOpenEFragment f = new DialogMineOpenEFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();//detail
//        args.putString("detail", detail);//
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        //去掉这个显示与屏幕边的缝隙
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        //对话框背景色
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );

        //其它
//        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
//////        UserConstant.mhight=wm.getDefaultDisplay().getHeight();//屏幕高度
        final WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width= dm.widthPixels-180;//wm.getDefaultDisplay().getWidth()
        layoutParams.height = getDialog().getWindow().getAttributes().height;//
        layoutParams.gravity = Gravity.CENTER;//
        getDialog().getWindow().setAttributes(layoutParams);

    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().setCanceledOnTouchOutside(true);//可以点击对话框外
    }

    public ImageButton img_btn_close;
    public Button btn_mine_open_e;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_open_e, null);
//        view.findViewById(R.id.lingear_updat).setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.transparent)));

//        final Dialog dialog = new Dialog(getActivity(), R.style.UpdateDialog);
//        dialog.setContentView(view);

        img_btn_close = (ImageButton) view.findViewById(R.id.img_btn_close);
        btn_mine_open_e = (Button) view.findViewById(R.id.btn_mine_open_e);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        // Add action buttons

        //
        img_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //
        btn_mine_open_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnOpenEListener listener = (OnOpenEListener) getActivity();
                listener.onOpenEAccount(v.getId());
                dismiss();
            }
        });

//        builder.create()
        return builder.create();//dialog
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode==KeyEvent.KEYCODE_BACK)
//                    return true;
                return false;
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
