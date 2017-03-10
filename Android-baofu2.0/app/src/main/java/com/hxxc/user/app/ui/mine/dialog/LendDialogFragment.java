package com.hxxc.user.app.ui.mine.dialog;

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
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hxxc.user.app.R;
import com.hxxc.user.app.ui.dialogfragment.LendDialogListener;
import com.squareup.picasso.Picasso;

/**
 * LendDialogFragment lendDialogFragment = new LendDialogFragment().newInstance(updateModel.getContents(), true);
 lendDialogFragment.show(getFragmentManager(), "lendDialogFragment");
 * Created by houwen.lai on 2016/11/4.
 * 出借规则
 *
 */

public class LendDialogFragment  extends DialogFragment {

    private ImageButton img_btn_close_member;
    private Button btn_member_lend;
    private ImageView img_member_lend;

    private WebView webView;

    private ProgressBar progressbar;

    public LendDialogFragment() {
    }


    public static LendDialogFragment newInstance(String url) {
        LendDialogFragment f = new LendDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("url", url);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        layoutParams.width= dm.widthPixels-80;//wm.getDefaultDisplay().getWidth()
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
        View view = inflater.inflate(R.layout.dialog_lend, null);
//        view.findViewById(R.id.lingear_updat).setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.transparent)));

//        final Dialog dialog = new Dialog(getActivity(), R.style.UpdateDialog);
//        dialog.setContentView(view);

        img_btn_close_member = (ImageButton) view.findViewById(R.id.img_btn_close_member);
        img_member_lend = (ImageView) view.findViewById(R.id.img_member_lend);
        btn_member_lend = (Button) view.findViewById(R.id.btn_member_lend);

        webView = (WebView) view.findViewById(R.id.web_integer);
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar_rule_lend);


//        Picasso.with(getActivity())
//        .load("url")
//        .placeholder(R.mipmap.icon_my_head)
//        .error(R.mipmap.icon_my_head)
//        .into( img_member_lend);



        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(false);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setAllowFileAccess(true);
//      webView.getSettings().setPluginsEnabled(true);support flash
//        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(false);//support zoom
        webView.getSettings().setUseWideViewPort(false);// 这个很关键
        webView.getSettings().setLoadWithOverviewMode(false);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        webView.getSettings().setDefaultFontSize(12);
//        webView.requestFocus();
        webView.getSettings().setLoadWithOverviewMode(false);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存：
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存：
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//		webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getArguments().getString("url"));
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 98) {
                    progressbar.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                } else {
                    progressbar.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        img_btn_close_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btn_member_lend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                LendDialogListener listener = (LendDialogListener) getActivity();
                listener.onLendId(view.getId());
                dismiss();
            }
        });

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        // Add action buttons

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
