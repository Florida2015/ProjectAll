package com.hxxc.user.app.ui.discovery;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.Department;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.ui.adapter.DepartmentPicsAdapter;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.ui.index.AdsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenqun on 2016/6/27.
 */
public class DepartmentDefaultActivity extends ToolbarActivity {
    @BindView(R.id.name_text)
    TextView name_text;
    @BindView(R.id.tele_text)
    TextView tele_text;
    @BindView(R.id.address_text)
    TextView address_text;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.nodata)
    TextView nodata;

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressbar_department)
    ProgressBar progressbar;

    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    TextView button2;
    @BindView(R.id.bigImage)
    ImageButton bigImage;
    private DepartmentPicsAdapter departmentAdapter;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_department_default;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("服务网点");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private Department department;
    private List<String> list;

    private void initView() {
        department = (Department) getIntent().getSerializableExtra("department");
        name_text.setText(department.getName());
        tele_text.setText(department.getTelephone());
        address_text.setText(department.getAddress());

        list = department.getRealPicUrls();
        if (list == null || list.size() == 0 || (list.size() == 1 && TextUtils.isEmpty(list.get(0)))) {
            nodata.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
            if(null == list){
                list = new ArrayList<>();
            }
        }
        departmentAdapter = new DepartmentPicsAdapter(this, list);
        gridView.setAdapter(departmentAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent in = new Intent(DepartmentDefaultActivity.this, PhotoActivity.class);
                in.putExtra("position", position);
                in.putStringArrayListExtra("list", (ArrayList<String>) list);
                in.putExtra("url", list.get(position));
                startActivity(in);
            }
        });
        getMap(department.getDid());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void getMap(int did) {
        webView.getSettings().setJavaScriptEnabled(true);
        String url = HttpRequest.getDepartmentMap + "?dids=" + did;
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {

            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    @OnClick({R.id.button2, R.id.bigImage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                checkCallPermission();
                break;

            case R.id.bigImage:
                showBigImage();
                break;
        }
    }

    private void checkCallPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, callPermission);
            }else{
                takePhone();
            }
        }else{
            takePhone();
        }
    }
    private static final int callPermission = 10;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == callPermission) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takePhone();
            }
        }
    }

    private void showBigImage() {
        String url = HttpRequest.getDepartmentMap + "?dids=" + department.getDid();
        Intent in = new Intent(DepartmentDefaultActivity.this, AdsActivity.class);
        in.putExtra("title", "服务网点");
        in.putExtra("url", url);
        in.putExtra("reflush",false);
        startActivity(in);
    }

    public void takePhone() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tele_text.getText().toString().trim()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }
}
