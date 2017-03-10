package com.huaxia.finance.mangemoneydm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framwork.Utils.BtnUtils;
import com.framwork.Utils.IDCardUtil;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.SharedPreferencesUtils;
import com.framwork.Utils.StringsUtils;
import com.framwork.Utils.ToastUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.base.BaseActivity;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.model.BankItemModel;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.CashVoucherModel;
import com.huaxia.finance.model.ProductDetailModel;
import com.huaxia.finance.model.UrlModel;
import com.huaxia.finance.model.UserInfoModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证页面
 * Created by houwen.lai on 2016/2/1.
 */
public class ApproveActivity extends BaseActivity implements View.OnClickListener{
    private final String mPageName = ApproveActivity.class.getSimpleName();

    private EditText ed_username;
    private EditText ed_usercard;
    private RelativeLayout relative_bank;
    private EditText ed_userbank;

    private TextView tv_choice_bank_name;
    private TextView tv_choice_next;

    private RelativeLayout relatice_bank_bg;
    private ImageView img_bank_icon;
    private TextView tv_name_bank;
    private TextView tv_name_detail;

    private Button btn_approve;

    private final int RequestCode = 0x01;

    BankItemModel model;
    private ProductDetailModel productDetailModel;
    private CashVoucherModel cashVoucherModel;//优惠券

    private String textTitle;

    private int PayMoney;
    private String productId;
    private String varchId;

    private Map<String,Integer> icon_bank=new HashMap<String,Integer>();

    private UserInfoModel userInfoModel;
    private Context mContext;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RequestCode&&resultCode==RESULT_OK){
            model = (BankItemModel) data.getSerializableExtra("bank");
            if (model!=null){
                MyLog.d("api_approve=" + model.toString());


                relative_bank.setVisibility(View.GONE);
                relatice_bank_bg.setVisibility(View.VISIBLE);

                img_bank_icon.setImageResource(icon_bank.get(model.getBankCode()));
                String bankNo = model.getPkId();
                if (TextUtils.isEmpty(model.getPkId())){
                    tv_name_bank.setText(model.getBankName());
                }else{
                    tv_name_bank.setText(model.getBankName()+" 尾号"+model.getPkId().substring(bankNo.length()-4,bankNo.length()));
                }

                if (TextUtils.isEmpty(model.getDailyLimit())){
                    tv_name_detail.setText(new StringBuffer("单笔限额").append(model.getSingleLimit()).append("万,").append("单日不限").toString());
                }else{
                    tv_name_detail.setText(new StringBuffer("单笔限额").append(model.getSingleLimit()).append("万,").append("单日").append(model.getDailyLimit()).append("万").toString());
                }
            }else {
                relative_bank.setVisibility(View.VISIBLE);
                relatice_bank_bg.setVisibility(View.GONE);
                tv_choice_next.setText("选择");
            }
        }

    }

    private static ApproveActivity instance=null;
    public static void finshApproveActivity(){
        if (instance!=null){
            instance.setResult(RESULT_OK);
            instance.finish();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        // SDK已经禁用了基于Activity 的页面统计，所以需要再次重新统计页面
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // SDK已经禁用了基于Activity 的页面统计，所以需要再次重新统计页面
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.approve_activity);
        instance = this;
        mContext = this;
        textTitle = getIntent().getStringExtra("title");
        model = (BankItemModel) getIntent().getSerializableExtra("bank");
        if (model!=null)MyLog.d("api_bank_item="+model.toString());
        productDetailModel = (ProductDetailModel) getIntent().getSerializableExtra("product");
        cashVoucherModel = (CashVoucherModel) getIntent().getSerializableExtra("vard");
        if (productDetailModel!=null){
            productId = productDetailModel.getProductId();
        }

        if (cashVoucherModel!=null&&!TextUtils.isEmpty(cashVoucherModel.getPkid())){
            varchId = cashVoucherModel.getPkid();
        }else varchId="null";
        PayMoney =getIntent().getIntExtra("money",0);

        setUserHttpRequest();

        findAllViews();

    }

    public void findAllViews(){
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_back.setVisibility(View.VISIBLE);
        img_btn_title_right.setVisibility(View.GONE);
        tv_title_bar.setText(TextUtils.isEmpty(textTitle) ? "实名认证" : textTitle);
        img_btn_title_back.setOnClickListener(this);

        icon_bank=new HashMap<String,Integer>();
        icon_bank.put("ICBC", R.drawable.bank_gongshang);
        icon_bank.put("CEB",R.drawable.bank_guangda);
        icon_bank.put("GDB",R.drawable.bank_guangfa);
        icon_bank.put("GZCB",R.drawable.bank_guangzhou);
        icon_bank.put("GRCB",R.drawable.bank_guangzhounongshang);
        icon_bank.put("HXB",R.drawable.bank_huaxia);
        icon_bank.put("CCB",R.drawable.bank_jianshe);
        icon_bank.put("CMBC",R.drawable.bank_minsheng);
        icon_bank.put("PAB",R.drawable.bank_pingan);
        icon_bank.put("SDB",R.drawable.bank_shenfazhan);
        icon_bank.put("BOC",R.drawable.bank_zhongguo);
        icon_bank.put("CITIC",R.drawable.bank_zhongxin);
        icon_bank.put("PSBC",R.drawable.bank_youzheng);
        icon_bank.put("SPDB",R.drawable.bank_pufa);
        icon_bank.put("CIB",R.drawable.bank_xingye);
        icon_bank.put("CMB",R.drawable.bank_zhaoshang);

        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_usercard= (EditText) findViewById(R.id.ed_usercard);
        relative_bank= (RelativeLayout) findViewById(R.id.relative_bank);
        ed_userbank= (EditText) findViewById(R.id.ed_userbank);
        btn_approve= (Button) findViewById(R.id.btn_approve);
        tv_choice_bank_name= (TextView) findViewById(R.id.tv_choice_bank_name);
        tv_choice_next= (TextView) findViewById(R.id.tv_choice_next);

        relatice_bank_bg= (RelativeLayout) findViewById(R.id.relatice_bank_bg);
        relatice_bank_bg.setOnClickListener(this);
        img_bank_icon= (ImageView) findViewById(R.id.img_bank_icon);
        tv_name_bank= (TextView) findViewById(R.id.tv_name_bank);
        tv_name_detail= (TextView) findViewById(R.id.tv_name_detail);

        if (model!=null){
            relative_bank.setVisibility(View.GONE);
            relatice_bank_bg.setVisibility(View.VISIBLE);

            img_bank_icon.setImageResource(icon_bank.get(model.getBankCode()));
            String bankNo = model.getPkId();
            if (TextUtils.isEmpty(model.getPkId())){
                tv_name_bank.setText(model.getBankName());
            }else{
                tv_name_bank.setText(model.getBankName()+" 尾号"+model.getPkId().substring(bankNo.length()-4,bankNo.length()));
            }

           if (TextUtils.isEmpty(model.getDailyLimit())){
               tv_name_detail.setText(new StringBuffer("单笔限额").append(model.getSingleLimit()).append("元,").append("单日不限").toString());
            }else{
               tv_name_detail.setText(new StringBuffer("单笔限额").append(model.getSingleLimit()).append("元,").append("单日").append(model.getDailyLimit()).append("元").toString());
            }
        }else {
            relative_bank.setVisibility(View.VISIBLE);
            relatice_bank_bg.setVisibility(View.GONE);
            tv_choice_next.setText("选择");
        }

        btn_approve.setOnClickListener(this);
        relative_bank.setOnClickListener(this);
    }

    public void InitData(String status,String userName,String certNo){
        if (!TextUtils.isEmpty(status)&&status.equals("0")&&!TextUtils.isEmpty(userName)){
            ed_username.setText(userName);
            ed_username.setSelection(userName.length());
            ed_username.setEnabled(false);
            ed_username.setFocusableInTouchMode(false);
        }else{
            ed_username.setEnabled(true);
            ed_username.setFocusableInTouchMode(true);
        }

        if (!TextUtils.isEmpty(status)&&status.equals("0")&&!TextUtils.isEmpty(certNo)){
            ed_usercard.setText(certNo);
            ed_usercard.setSelection(certNo.length());
            ed_usercard.setEnabled(false);
            ed_usercard.setFocusableInTouchMode(false);
        }else{
            ed_usercard.setEnabled(true);
            ed_usercard.setFocusableInTouchMode(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_title_back://
                finish();
                break;
            case R.id.relative_bank://选择银行卡
            case R.id.relatice_bank_bg:
                startActivityForResult(new Intent(ApproveActivity.this, BankListActivity.class), RequestCode);

                break;
            case R.id.btn_approve://
                if (BtnUtils.isFastDoubleClick()){
                    requestDate();
                }
                break;
        }
    }

    public void requestDate(){
        String name =  ed_username.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            ToastUtils.showShort("请输入姓名");
            return;
        }
//        if (StringsUtils.getNameIs(name)){
//            ToastUtils.showShort("姓名为中文");
//            return;
//        }

        String Idcard =  ed_usercard.getText().toString().trim();
        if (TextUtils.isEmpty(Idcard)){
            ToastUtils.showShort("请输入证件号");
            return;
        }else {
            String IDText = IDCardUtil.IDCardValidate(Idcard);
            if (!TextUtils.isEmpty(IDText)){
                ToastUtils.showShort(IDText);
            }
        }

        if (model==null){
            ToastUtils.showShort("请选择开户行");
            return;
        }

        String bankNumber = ed_userbank.getText().toString().trim();
        if (TextUtils.isEmpty(bankNumber)){
            ToastUtils.showShort("请输入银行卡号");
            return;
        }
        btn_approve.setEnabled(false);
        if (productDetailModel!=null){
            if (!TextUtils.isEmpty(productId))
                setPayHttpRequest(name,Idcard,bankNumber,model,productId,varchId);
        }else{
            setHttpRequest(name,Idcard,bankNumber,model);
        }
    }

    /**
     * 实名认证
     */
    public void setHttpRequest(String name,String card,String bankNumber,BankItemModel model){
        relative_no_work.setVisibility(View.VISIBLE);
        img_empty.setVisibility(View.GONE);
        tv_reloading.setVisibility(View.GONE);
        ballview.setVisibility(View.VISIBLE);

        BaseRequestParams params = new BaseRequestParams();
        params.put("userName",name);
        params.put("certNo",card);
        params.put("cardNo",bankNumber);
        params.put("bankCode", model.getBankCode());
        String url = UrlConstants.urlBase+UrlConstants.urlPaymentVerfication;
        MyLog.d("api="+url+"_username="+name+"_certNo="+card+"_cardNo="+bankNumber+"_bankcode="+model.getBankCode());
        DMApplication.getInstance().getHttpClient(this).post(this, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                btn_approve.setEnabled(true);
                relative_no_work.setVisibility(View.GONE);
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<UrlModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<UrlModel>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {

                            startActivity(new Intent(ApproveActivity.this,PayWebActivity.class).putExtra("url",baseModel.getData().getUrl()).putExtra("temp","2"));
                        }else  ToastUtils.showShort(baseModel.getMsg());

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");
                btn_approve.setEnabled(true);
                relative_no_work.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 实名认证并支付
     */
    public void setPayHttpRequest(String name,String card,String bankNumber,BankItemModel model,String productId,String varId){
        relative_no_work.setVisibility(View.VISIBLE);
        img_empty.setVisibility(View.GONE);
        tv_reloading.setVisibility(View.GONE);
        ballview.setVisibility(View.VISIBLE);

        BaseRequestParams params = new BaseRequestParams();
        params.put("userName", name);//
        params.put("certNo", card);//身份证号
        params.put("cardNo", bankNumber);//银行卡号
        params.put("bankCode", model.getBankCode());//银行卡代码 例如 ICBC

        params.put("productId",productId);//要购买的产品id
        params.put("amount",""+PayMoney);//支付金额
        params.put("discountVoucherPkId",varId);//优惠券discountVoucherPkId
        params.put("orderSrc","3");//来源 1.WAP 2.IOS 3.安卓 4.网站
        String url = UrlConstants.urlBase+UrlConstants.urlPaymentFirstPay;
        MyLog.d("api_first_pay_url="+url+"_userName="+name+"_certNo="+card+"_cardNo="+bankNumber+"_bankCode="+model.getBankCode()+"_productId="+productId+"_amount="+PayMoney+"_discountVoucherId="+varId);
        DMApplication.getInstance().getHttpClient(this).post(this, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                btn_approve.setEnabled(true);
                relative_no_work.setVisibility(View.GONE);
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<UrlModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<UrlModel>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                            ToastUtils.showShort(baseModel.getMsg());
                            startActivity(new Intent(ApproveActivity.this,PayWebActivity.class).putExtra("url",baseModel.getData().getUrl()).putExtra("temp", "1").putExtra("orderId",baseModel.getData().getOrderInfo().getOrderId()));
                        }else if(baseModel.getStatus().equals(StateConstant.Status_Order_010)){//绑卡失败
                            ToastUtils.showShort("绑卡失败");
                        }else if(baseModel.getStatus().equals(StateConstant.Status_Order_30)||baseModel.getStatus().equals(StateConstant.Status_Order_031)){
                            ToastUtils.showShort(baseModel.getMsg());
                        } else  ToastUtils.showShort(baseModel.getMsg());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");
                btn_approve.setEnabled(true);
                relative_no_work.setVisibility(View.GONE);
            }
        });
    }


    public void setUserHttpRequest(){
        BaseRequestParams params = new BaseRequestParams();
        String url = UrlConstants.urlBase+UrlConstants.urlAccountUserInfo;
        MyLog.d("api_=" + url);
        DMApplication.getInstance().getHttpClient(this).post(this, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<UserInfoModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<UserInfoModel>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                            userInfoModel = baseModel.getData();
                            SharedPreferencesUtils.getInstanse().putKeyValue(mContext, UserConstant.key_username,userInfoModel.getUserName());
                            SharedPreferencesUtils.getInstanse().putKeyValue(mContext,UserConstant.key_userauthnamestatus,userInfoModel.getAuthnameStatus());
                            InitData(userInfoModel.getAuthnameStatus(),userInfoModel.getUserName(),userInfoModel.getCertNo());

                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");

            }
        });
    }

}
