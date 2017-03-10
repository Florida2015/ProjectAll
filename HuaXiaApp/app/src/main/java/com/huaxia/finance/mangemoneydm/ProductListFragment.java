package com.huaxia.finance.mangemoneydm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framwork.Utils.DateUtils;
import com.framwork.Utils.FileUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.NetWorkUtils;
import com.framwork.Utils.StringsUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.framwork.loadingballs.BallView;
import com.framwork.pullrefresh.ui.PullToRefreshBase;
import com.framwork.pullrefresh.ui.PullToRefreshListView;
import com.framwork.widget.RoundProgressBar;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.adapter.CommonAdapter;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UmengConstants;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.ProductItemModel;
import com.huaxia.finance.model.ProductListModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 产品列表
 * Created by houwen.lai on 2016/1/18.
 */
public class ProductListFragment extends Fragment {



    public interface ProductListChangeListener {
        public void TextChangeOne(String text);
        public void TextChangeTwo(String text);

    }

    private View view_add_warming;

    private RelativeLayout relative_no_work;
    private ImageView img_empty;
    private TextView tv_reloading;
    private BallView ballview;

    private String mPageName;
    int mNum;

    PullToRefreshListView pull_listview_product;

    private TextView tv_title_item;
    private TextView tv_img_tip;

    private TextView tv_product_rate;//收益率
    private RoundProgressBar probar_product;
    private TextView tv_open_time;
    private ImageView img_none_product;

    private TextView tv_min_money_sum_money;// 起投金额 融资金额

    ProductAdapter productAdapter;
    private List<ProductItemModel> productItemModelList;
    private boolean flagRefresh=false;

    private int pageSize=0;

    private boolean flagCount=true;
    private final int msg_code=0x001;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msg_code://
                    if(flagCount){
                        MyLog.d("count_time=s");
                        CountTime();
                        handler.sendEmptyMessageDelayed(msg_code,1000);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     *
     */
    public void CountTime(){
        if (productItemModelList==null) {
            flagCount=false;
            return;
        }
        if (productItemModelList.size()==0){
            flagCount=false;
            return;
        }
        boolean temp=false;
//        MyLog.d("api_couttiem");
        for (int i=0;i<productItemModelList.size();i++){
            if ((productItemModelList.get(i).getStatus()==1||productItemModelList.get(i).getStatus()==2)&&(productItemModelList.get(i).getProductStart()-productItemModelList.get(i).getServerTime())>0){
                long time = productItemModelList.get(i).getServerTime()+1000;
                productItemModelList.get(i).setServerTime(time);
                temp=true;
            }
        }
        if (temp){
            productAdapter.clearAdapterListData();
            productAdapter.setAdapterListData(-1, productItemModelList);
            productAdapter.notifyDataSetChanged();

//            productAdapter = new ProductAdapter(getActivity(),productItemModelList,R.layout.product_item);
//            pull_listview_product.getRefreshableView().setAdapter(productAdapter);
        }
        flagCount=temp;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(msg_code);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onPageStart(mPageName);

        relative_no_work = (RelativeLayout)getActivity().findViewById(R.id.relative_no_work);
        relative_no_work.setVisibility(View.GONE);
        img_empty = (ImageView)getActivity().findViewById(R.id.img_empty);
        tv_reloading = (TextView)getActivity().findViewById(R.id.tv_reloading);
        ballview = (BallView)getActivity().findViewById(R.id.ballview);

        view_add_warming = View.inflate(getActivity(),R.layout.text_waming_invest,null);

        pull_listview_product= (PullToRefreshListView) getActivity().findViewById(R.id.pull_listview_product);
        pull_listview_product.setPullRefreshEnabled(true);
        pull_listview_product.setPullLoadEnabled(true);
        pull_listview_product.setScrollLoadEnabled(false);
        pull_listview_product.setHasMoreData(true);
        pull_listview_product.getRefreshableView().setFastScrollEnabled(false);
        pull_listview_product.getRefreshableView().setDivider(null);
        pull_listview_product.getRefreshableView().setDividerHeight(0);
        pull_listview_product.getRefreshableView().setVerticalScrollBarEnabled(false);
        pull_listview_product.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                String text = DateUtils.getInstanse().getNowDate(DateUtils.YYYYMMDDHHMMSS);
                pull_listview_product.setLastUpdatedLabel(text);
                pageSize=0;
                flagRefresh=true;
                RequestDate();
//                pull_listview_product.onPullDownRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载更多
//                pull_listview_product.onPullUpRefreshComplete();
                flagRefresh=false;
                RequestDate();
            }
        });
        pull_listview_product.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //进入产品说明页
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("productId",productAdapter.getItem(position).getProductId()+"");
                map.put("productName",productAdapter.getItem(position).getProductName()+"");
                map.put("productIdClick","productIdClick");
                MobclickAgent.onEvent(getActivity(), UmengConstants.huaxia_200, map);
                getActivity().startActivity(new Intent(getActivity(), ProductExplainActivity.class).putExtra("produnctId",productAdapter.getItem(position).getProductId()));

            }
        });

        //请求数据
        if (NetWorkUtils.isNetworkConnected(getActivity())){
            relative_no_work.setVisibility(View.GONE);
            pull_listview_product.doPullRefreshing(true,100);
        }else{

            String textP = FileUtils.getInstance().readFileToSdcardByFileInputStream(UserConstant.pathCache,UserConstant.pathProductList);
            MyLog.d("text_productlist=" + textP);

            if (!TextUtils.isEmpty(textP))  jsonProductListData(textP);
            else {
                relative_no_work.setVisibility(View.VISIBLE);
                img_empty.setVisibility(View.VISIBLE);
                img_empty.setImageResource(R.drawable.icon_no_wifi);
                tv_reloading.setVisibility(View.VISIBLE);
                tv_reloading.setText("网络出现问题啦!");
            }
        }

    }
    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        mPageName = String.format("fragment %d", mNum);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (container == null) {
            // Currently in a layout without a container, so no
            // reason to create our view.
            return null;
        }
        LayoutInflater myInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = myInflater.inflate(R.layout.product_fragment, container,
                false);
        return view;
    }

    public void InitDates(){
        if (productItemModelList==null)productItemModelList=new ArrayList<ProductItemModel>();
        productItemModelList.clear();
        productAdapter = new ProductAdapter(getActivity(),productItemModelList,R.layout.product_item);
        pull_listview_product.getRefreshableView().setAdapter(productAdapter);
    }

    public void setListDate(ProductListModel model){
        if (model==null)return;

        if (model.getList()==null)return;
        if (productItemModelList==null)productItemModelList=new ArrayList<ProductItemModel>();
        if (pageSize==0){
            productItemModelList.clear();
        }
        productItemModelList.addAll(model.getList());
        handler.removeMessages(msg_code);

        if (productAdapter==null) {
            productAdapter = new ProductAdapter(getActivity(),model.getList(),R.layout.product_item);
            pull_listview_product.getRefreshableView().addFooterView(view_add_warming);
            pull_listview_product.getRefreshableView().setAdapter(productAdapter);
        } else {
            if (pageSize==0){
                productAdapter.clearAdapterListData();
            }
            productAdapter.setAdapterListData(-1, model.getList());
            productAdapter.notifyDataSetChanged();
        }

        pageSize = pageSize+10;

        if (productAdapter.getAdapterListData().size()==0){
            relative_no_work.setVisibility(View.VISIBLE);
            pull_listview_product.setVisibility(View.GONE);
            img_empty.setVisibility(View.VISIBLE);
            img_empty.setImageResource(R.drawable.icon_empty_a);
            tv_reloading.setVisibility(View.GONE);
        }else{
            relative_no_work.setVisibility(View.GONE);
            pull_listview_product.setVisibility(View.VISIBLE);
        }

        handler.sendEmptyMessage(msg_code);

    }

    /**
     * json
     */
    public void jsonProductListData(String result){

        BaseModel<ProductListModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<ProductListModel>>() {
        }.getType());
        MyLog.d("api=___aaaaa____");
        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
            flagCount=true;
            setListDate(baseModel.getData());

        }else{
            if (productAdapter!=null){
                if (productAdapter.getCount()==0){
                    relative_no_work.setVisibility(View.VISIBLE);
                    img_empty.setVisibility(View.VISIBLE);
                    img_empty.setImageResource(R.drawable.icon_empty_a);
                    tv_reloading.setVisibility(View.GONE);
                }
            }else{
                relative_no_work.setVisibility(View.VISIBLE);
                img_empty.setVisibility(View.VISIBLE);
                img_empty.setImageResource(R.drawable.icon_empty_a);
                tv_reloading.setVisibility(View.GONE);
            }
        }

    }


    /**
     * 控件初始化
     */
    public void RequestDate(){
        final BaseRequestParams params = new BaseRequestParams();
        params.put("start",""+pageSize);
        params.put("size", "" + 10);
        StringBuffer url = new StringBuffer(UrlConstants.urlBase);
        url.append(UrlConstants.urlProductList);
        DMApplication.getInstance().getHttpClient(getActivity()).get(getActivity(), url.toString().trim(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                if (flagRefresh) pull_listview_product.onPullDownRefreshComplete();
                else pull_listview_product.onPullUpRefreshComplete();
                try {
                    String resultDate = StringsUtils.getBytetoString(responseBody, "UTF-8");
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + resultDate);

                    FileUtils.getInstance().writeFileToSdcardByFileOutputStream(UserConstant.pathCache, UserConstant.pathProductList, resultDate);

                    if (statusCode == 200) {

                        jsonProductListData(resultDate);

                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");
                if (flagRefresh) pull_listview_product.onPullDownRefreshComplete();
                else pull_listview_product.onPullUpRefreshComplete();
                if (productAdapter!=null){
                    if (productAdapter.getCount()==0){
                        relative_no_work.setVisibility(View.VISIBLE);
                        img_empty.setVisibility(View.VISIBLE);
                        img_empty.setImageResource(R.drawable.icon_no_wifi);
                        tv_reloading.setVisibility(View.VISIBLE);
                        tv_reloading.setText("网络出现问题啦!");
                    }
                }else{
                    relative_no_work.setVisibility(View.VISIBLE);
                    img_empty.setVisibility(View.VISIBLE);
                    img_empty.setImageResource(R.drawable.icon_no_wifi);
                    tv_reloading.setVisibility(View.VISIBLE);
                    tv_reloading.setText("网络出现问题啦!");
                }
            }
        });
    }

    /**
     * 产品适配器
     */
    private class ProductAdapter extends CommonAdapter<ProductItemModel> {

        public ProductAdapter(Context context, List<ProductItemModel> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, ProductItemModel item) {
            //设置值
            helper.setText(R.id.tv_title_item, new StringBuffer("[ ").append(item.getProductName()).append(" ] 第").append(item.getProductNum()).append("期").toString());

            TextView tv_img_tip=(TextView) helper.getView(R.id.tv_img_tip);
            RelativeLayout relative_probar_product=(RelativeLayout) helper.getView(R.id.relative_probar_product);
            RoundProgressBar roundProgressBar=(RoundProgressBar) helper.getView(R.id.probar_product);
            LinearLayout linear_open_time=(LinearLayout) helper.getView(R.id.linear_open_time);
            TextView tv_open_time_hour=(TextView) helper.getView(R.id.tv_open_time_hour);
            TextView tv_open_time_min=(TextView) helper.getView(R.id.tv_open_time_min);
            TextView tv_open_time_s=(TextView) helper.getView(R.id.tv_open_time_s);
            ImageView img_over = (ImageView)helper.getView(R.id.img_none_product);

            if (!TextUtils.isEmpty(item.getActivityType())){
                if (item.getActivityType().equals("1")){//1 新手标
                    tv_img_tip.setVisibility(View.VISIBLE);
                    tv_img_tip.setBackgroundResource(R.drawable.tip_new_buyer);
                }else if(item.getActivityType().equals("2")){//2 转发类活动
                    tv_img_tip.setVisibility(View.VISIBLE);
                    tv_img_tip.setBackgroundResource(R.drawable.tip_relay);
                }else if(item.getActivityType().equals("3")){//3 增益类活动
                    tv_img_tip.setVisibility(View.VISIBLE);
                    tv_img_tip.setBackgroundResource(R.drawable.tip_add_income);
                }else if(item.getActivityType().equals("4")){//4 送优惠券活动
                    tv_img_tip.setVisibility(View.VISIBLE);
                    tv_img_tip.setBackgroundResource(R.drawable.tip_coupon);
                }else if(item.getActivityType().equals("5")){//5 实物类活动
                    tv_img_tip.setVisibility(View.VISIBLE);
                    tv_img_tip.setBackgroundResource(R.drawable.tip_entity);
                }else if(item.getActivityType().equals("6")){//6 其他
                    tv_img_tip.setVisibility(View.VISIBLE);
                    tv_img_tip.setBackgroundResource(R.drawable.tip_others);
                }else tv_img_tip.setVisibility(View.GONE);
            }else tv_img_tip.setVisibility(View.GONE);

            TextView tv_product_rate = ((TextView)helper.getView(R.id.tv_product_rate));
            tv_product_rate.setText(DateUtils.getInstanse().getOne(item.getYield()));
            tv_product_rate.setTextColor(getActivity().getResources().getColor(R.color.orange_ff92));

            ((TextView)helper.getView(R.id.tv_product_rate_percent)).setTextColor(getActivity().getResources().getColor(R.color.orange_ff92));

            if (item.getStatus()==2||item.getStatus()==1){//2.待售
                if(item.getProductStart()-item.getServerTime()<1000){
                    linear_open_time.setVisibility(View.GONE);
                    img_over.setVisibility(View.GONE);
                    relative_probar_product.setVisibility(View.VISIBLE);

                    roundProgressBar.setDisplayType(false);
                    roundProgressBar.setMax(100);
                    float poor = (item.getAmount()-item.getRemainingNum())/item.getAmount();
                    MyLog.d("api_poor=" + poor);
                    if (poor<=0){
                        roundProgressBar.setProgress(0);
                    }else if (poor<0.01){
                        roundProgressBar.setProgress(1);
                    }else
                        roundProgressBar.setProgress((int)(100*poor));
                }else{
                    relative_probar_product.setVisibility(View.GONE);
                    linear_open_time.setVisibility(View.VISIBLE);
                    img_over.setVisibility(View.GONE);
//                DateUtils.getInstanse().getNowTime() item.getServerTime()
//                    String opentime = DateUtils.getmstodated(item.getProductStart()-item.getServerTime(),DateUtils.HHMMSS);
//                    MyLog.d("count_opentime="+item.getMinuSecond()+"___hhmmsss="+opentime);
//                    tv_open_time_hour.setText("" + opentime.substring(0, 2));
//                    tv_open_time_min.setText(""+opentime.substring(3,opentime.length()-3));
//                    tv_open_time_s.setText(""+opentime.substring(opentime.length()-2,opentime.length()));
                   String[] mt = DateUtils.getHourMinSec((int)(item.getProductStart()/1000-item.getServerTime()/1000));
                    tv_open_time_hour.setText(mt[0]);
                    tv_open_time_min.setText(mt[1]);
                    tv_open_time_s.setText(mt[2]);
                }
//                String opentime=DateUtils.getmstodate(item.getServerTime()-item.getProductStart(),DateUtils.HHMMSS);
//                SpannableString mspp = new SpannableString(opentime+"\n后开售");
//                // 设置字体背景色
//                mspp.setSpan(new BackgroundColorSpan(getActivity().getResources().getColor(R.color.black_ecec)), 0, 2,
//                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置背景色为青色
//                mspp.setSpan(new BackgroundColorSpan(getActivity().getResources().getColor(R.color.black_ecec)), 3, 5,
//                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置背景色为青色
//                mspp.setSpan(new BackgroundColorSpan(getActivity().getResources().getColor(R.color.black_ecec)), 6, 8,
//                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置背景色为青色
//                mspp.setSpan(new AbsoluteSizeSpan(18, true), 0, mspp.length() - 3,
//                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                mspp.setSpan(new AbsoluteSizeSpan(16, true), mspp.length() - 3, mspp.length(),
//                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                tv_open_time.setText(mspp);
//                tv_open_time.setMovementMethod(LinkMovementMethod.getInstance());
//                handler.sendEmptyMessage(msg_code);
            }else if(item.getStatus()==3){//3.售磬
                relative_probar_product.setVisibility(View.GONE);
                linear_open_time.setVisibility(View.GONE);
                img_over.setVisibility(View.VISIBLE);
                tv_product_rate.setTextColor(getActivity().getResources().getColor(R.color.black_9999));
                ((TextView)helper.getView(R.id.tv_product_rate_percent)).setTextColor(getActivity().getResources().getColor(R.color.black_9999));
            }else {//4.封闭，5.到期，6.还款中，7.已还款
                relative_probar_product.setVisibility(View.GONE);
                linear_open_time.setVisibility(View.GONE);
                img_over.setVisibility(View.VISIBLE);
            }

            if (!TextUtils.isEmpty(item.getActivityAlias())){//活动别名
                helper.setText(R.id.tv_img_tip, item.getActivityAlias());
            }else helper.getView(R.id.tv_img_tip).setVisibility(View.GONE);



            StringBuffer textmoney = new StringBuffer("起投 ");
            textmoney.append(DateUtils.getInstanse().getInt(item.getMinPrice())).append("元").append("\n").append("总额 ");
            textmoney.append(DateUtils.getInstanse().getDataType((long)item.getAmount()));
            helper.setText(R.id.tv_min_money_sum_money, textmoney.toString());

        }
    }


}
