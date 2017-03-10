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
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.user.app.R;

/**
 * Created by Administrator on 2016/12/23.
 *
 * DialogSignRuleFragment updateAppDialogFragment = new DialogSignRuleFragment().newInstance(updateModel.getContents(), true);
 updateAppDialogFragment.show(getFragmentManager(), "DialogSignRuleFragment");
 */

public class DialogSignRuleFragment extends DialogFragment {

    private TextView tv_dialog_title;
    private WebView webView;
    private Button btn_rule_sign;

    private ProgressBar progressbar;

    private String url;
    private boolean flag;

    public DialogSignRuleFragment() {
    }

    public static DialogSignRuleFragment newInstance(String url,boolean flag) {
        DialogSignRuleFragment f = new DialogSignRuleFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();//detail
        args.putString("url", url);//
        args.putBoolean("flag", flag);//
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
        flag = getArguments().getBoolean("flag");
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
        View view = inflater.inflate(R.layout.dialog_rule_sign, null);
//        view.findViewById(R.id.lingear_updat).setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(R.color.transparent)));

//        final Dialog dialog = new Dialog(getActivity(), R.style.UpdateDialog);
//        dialog.setContentView(view);
        tv_dialog_title = (TextView) view.findViewById(R.id.tv_dialog_title);
        webView = (WebView) view.findViewById(R.id.web_sign_rule);
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar_rule_sign);

        btn_rule_sign= (Button) view.findViewById(R.id.btn_rule_sign);

        if (flag)tv_dialog_title.setVisibility(View.VISIBLE);
        else tv_dialog_title.setVisibility(View.GONE);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(false);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setAllowFileAccess(true);
//      webView.getSettings().setPluginsEnabled(true);support flash
//        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(false);//support zoom
        webView.getSettings().setUseWideViewPort(true);// 这个很关键
        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.requestFocus();
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存：
//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存：
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//		webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
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

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        // Add action buttons

        btn_rule_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

}
