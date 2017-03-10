package com.hxxc.user.app.ui.product.dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.FinancialPlanner;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.ImUtils;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.widget.CircleImageView;
import com.hxxc.user.app.widget.verticalpager.DragDialogLayout;
import com.squareup.picasso.Picasso;

/**
 * Created by chenqun on 2016/10/31.
 */

public class ProductDetailDialog extends Dialog implements View.OnClickListener {

    public ProductDetailDialog(Context context) {
        super(context, R.style.AddressDialog);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.button_im:
                ImUtils.getInstance().startPrivateChat(getContext());
                break;
            case R.id.button_phone:
                call();
                break;
        }
    }

    private void call() {//Intent.ACTION_CALL
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4000-466-600"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }
        getContext().startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_productdetail);
        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.button_im).setOnClickListener(this);
        findViewById(R.id.button_phone).setOnClickListener(this);
        DragDialogLayout content = (DragDialogLayout) findViewById(R.id.dl_content);
        content.setNextPageListener(new DragDialogLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
                content.post(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                });
            }
        });

        initViews();
        initPosition();
    }

    private void initViews() {
        FinancialPlanner financer = SPUtils.geTinstance().getFinancer();

        CircleImageView financial_icon = (CircleImageView) findViewById(R.id.financial_icon);
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_num = (TextView) findViewById(R.id.tv_num);
        TextView tv_area = (TextView) findViewById(R.id.tv_area);
        TextView tv_total_invest = (TextView) findViewById(R.id.tv_total_invest);
        TextView tv_total_custom = (TextView) findViewById(R.id.tv_total_custom);

        Picasso.with(getContext()).load(financer.getRealIcon()).error(R.drawable.default_financier_pic).placeholder(R.drawable.default_financier_pic).into(financial_icon);
        tv_name.setText(financer.getFname());
        tv_num.setText(financer.getFinancialno());
        tv_area.setText(financer.getSysDepartmentVo().getName());
        tv_total_invest.setText(CommonUtil.moneyType(financer.getInvestmentamout()/10000f)+"（万元）");
        tv_total_custom.setText(financer.getServicecount()+"（位）");
    }

    private void initPosition() {
        // 获取到窗体
        Window window = getWindow();
        // 获取到窗体的属性
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        // 让对话框展示到屏幕的下边
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }
}
