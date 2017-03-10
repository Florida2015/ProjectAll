package com.hxxc.user.app.ui.mine.sign;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.ui.dialogfragment.LendDialogListener;
import com.hxxc.user.app.ui.dialogfragment.SignDialogListener;

/**
 * * UpdateAppDialogFragment updateAppDialogFragment = new UpdateAppDialogFragment().newInstance(updateModel.getContents(), true);
 updateAppDialogFragment.show(getFragmentManager(), "updateAppDialogFragment");
 *
 * Created by houwen.lai on 2016/11/2.
 * 签到成功
 */

public class DialogSignFragment extends DialogFragment {

    private TextView tv_get_integer;
    private TextView tv_get_extra_integer;
    private Button btn_get_sign;

    private String integral_today;
    private String integral_day;
    private String integral_extra;

    public DialogSignFragment() {
    }

    public static DialogSignFragment newInstance(String integral_today,String integral_day,String integral_extra) {
        DialogSignFragment f = new DialogSignFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();//detail
        args.putString("integral_today", integral_today);//
        args.putString("integral_day", integral_day);
        args.putString("integral_extra", integral_extra);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        integral_today = getArguments().getString("integral_today");
        integral_day = getArguments().getString("integral_day");
        integral_extra = getArguments().getString("integral_extra");

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
        getDialog().setCanceledOnTouchOutside(true);//可以点击对话框外
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sign_success, null);
//        view.findViewById(R.id.lingear_updat).setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.transparent)));

//        final Dialog dialog = new Dialog(getActivity(), R.style.UpdateDialog);
//        dialog.setContentView(view);

        tv_get_integer = (TextView) view.findViewById(R.id.tv_get_integer);
        tv_get_extra_integer = (TextView) view.findViewById(R.id.tv_get_extra_integer);

        btn_get_sign= (Button) view.findViewById(R.id.btn_get_sign);

        final SpannableStringBuilder sp = new SpannableStringBuilder("今天签到获得"+integral_today+"积分");
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_aaaa)), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange_fbb0)), 6, sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(15, true), 0, sp.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        tv_get_integer.setText(sp);

        tv_get_extra_integer.setText("再连续签到"+integral_day+"天，额外奖励"+integral_extra+"积分");


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        // Add action buttons

        btn_get_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignDialogListener listener = (SignDialogListener) getActivity();
                listener.onSignId(view.getId());
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
                if (keyCode==KeyEvent.KEYCODE_BACK)
                    return true;
                return false;
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
