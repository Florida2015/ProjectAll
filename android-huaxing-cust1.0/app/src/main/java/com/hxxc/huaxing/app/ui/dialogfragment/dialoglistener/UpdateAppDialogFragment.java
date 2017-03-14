package com.hxxc.huaxing.app.ui.dialogfragment.dialoglistener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * UpdateAppDialogFragment updateAppDialogFragment = new UpdateAppDialogFragment().newInstance(updateModel.getContents(), true);
 updateAppDialogFragment.show(getFragmentManager(), "updateAppDialogFragment");
 *
 * Created by Administrator on 2016/7/12.
 */
public class UpdateAppDialogFragment extends DialogFragment {

    private TextView tv_content;
    private TextView tv_version;
    private FancyButton btn_update_fouse;
    private FancyButton btn_update_waite;
    private FancyButton btn_update_now;

    private boolean flag = false;

    private String detailContent;
    private String positionString;
    private String negativeString;

    private String versionName;

    public UpdateAppDialogFragment() {
    }

    public static UpdateAppDialogFragment newInstance(boolean flag,String detail) {
        UpdateAppDialogFragment f = new UpdateAppDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();//detail
        args.putString("detail", detail);//
        args.putBoolean("flag", flag);
        f.setArguments(args);
        return f;
    }

    public static UpdateAppDialogFragment newInstance(boolean flag,String detail,String versionName) {
        UpdateAppDialogFragment f = new UpdateAppDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("detail", detail);
        args.putBoolean("flag", flag);
        args.putString("versionName", versionName);
//        args.putString("negative", negativeButton);
        f.setArguments(args);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailContent = getArguments().getString("detail");
        flag = getArguments().getBoolean("flag");
        versionName = getArguments().getString("versionName");
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
        getDialog().setCanceledOnTouchOutside(false);//可以点击对话框外
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_app_update, null);
//        view.findViewById(R.id.lingear_updat).setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.transparent)));

//        final Dialog dialog = new Dialog(getActivity(), R.style.UpdateDialog);
//        dialog.setContentView(view);

        tv_version = (TextView) view.findViewById(R.id.tv_version);
        tv_content = (TextView) view.findViewById(R.id.tv_content_text);

        btn_update_fouse= (FancyButton) view.findViewById(R.id.btn_fouse);
        btn_update_waite= (FancyButton) view.findViewById(R.id.btn_cancle);
        btn_update_now= (FancyButton) view.findViewById(R.id.btn_sure);

        tv_version.setText("最新版本："+versionName);
        tv_content.setText(detailContent);

        if (flag){//是否强更
            btn_update_fouse.setVisibility(View.VISIBLE);
            btn_update_waite.setVisibility(View.GONE);
            btn_update_now.setVisibility(View.GONE);
        }else{
            btn_update_fouse.setVisibility(View.GONE);
            btn_update_waite.setVisibility(View.VISIBLE);
            btn_update_now.setVisibility(View.VISIBLE);
        }
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        // Add action buttons


        //强制更新
        btn_update_fouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_update_fouse.setBackgroundColor(getResources().getColor(R.color.blue_1447));
                OnUpdateAppListener listener = (OnUpdateAppListener) getActivity();
                listener.onUpdateApk(v.getId());
//                dismiss();
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
                UserInfoConfig.FlagUpdata=true;
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
                if (keyCode==KeyEvent.KEYCODE_BACK&&flag)
                    return true;
                return false;
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        OnUpdateAppListener listener = (OnUpdateAppListener) getActivity();
//        listener.onCancel();
//        Toast.makeText(getActivity(), "onCancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        OnUpdateAppListener listener = (OnUpdateAppListener) getActivity();
//        listener.onDismiss();
//        Toast.makeText(getActivity(), "onDismiss", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OnUpdateAppListener listener = (OnUpdateAppListener) getActivity();
//        listener.onDismiss();
    }



}
