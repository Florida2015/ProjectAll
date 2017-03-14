package com.hxxc.huaxing.app.ui.wealth.productdetail;

import android.view.View;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.ProductInfo;
import com.hxxc.huaxing.app.ui.wealth.BaseFragment2;
import com.hxxc.huaxing.app.utils.CommonUtil;
import com.hxxc.huaxing.app.wedgit.LeftAndRightTextView;
import com.hxxc.huaxing.app.wedgit.verticalpager.CustScrollView;

import butterknife.BindView;

/**
 * Created by chenqun on 2016/9/23.
 */

public class AFragment extends BaseFragment2 {
    @BindView(R.id.scrollview)
    CustScrollView scrollview;

    @BindView(R.id.tv_1)
    LeftAndRightTextView tv_1;//姓名
    @BindView(R.id.tv_2)
    LeftAndRightTextView tv_2;//年龄
    @BindView(R.id.tv_3)
    LeftAndRightTextView tv_3;//婚姻状况
    @BindView(R.id.tv_4)
    LeftAndRightTextView tv_4;//教育程度
    @BindView(R.id.tv_5)
    LeftAndRightTextView tv_5;//工作性质
    @BindView(R.id.tv_6)
    LeftAndRightTextView tv_6;//单位性质
    @BindView(R.id.tv_7)
    LeftAndRightTextView tv_7;//所属行业
    @BindView(R.id.tv_8)
    LeftAndRightTextView tv_8;//职位名称
    @BindView(R.id.tv_9)
    LeftAndRightTextView tv_9;//月收入
    @BindView(R.id.tv_10)
    LeftAndRightTextView tv_10;//房产类型
    @BindView(R.id.tv_11)
    LeftAndRightTextView tv_11;//户籍地址
    @BindView(R.id.tv_12)
    LeftAndRightTextView tv_12;//现居地址

    @BindView(R.id.tv_21)
    LeftAndRightTextView tv_21;//车辆品牌
    @BindView(R.id.tv_22)
    LeftAndRightTextView tv_22;//车辆型号
    @BindView(R.id.tv_23)
    LeftAndRightTextView tv_23;//车身颜色
    @BindView(R.id.tv_24)
    LeftAndRightTextView tv_24;//购买价格
    @BindView(R.id.tv_25)
    LeftAndRightTextView tv_25;//行驶公里数
    @BindView(R.id.tv_26)
    LeftAndRightTextView tv_26;//购买日期
    @BindView(R.id.tv_27)
    LeftAndRightTextView tv_27;//有无事故
    @BindView(R.id.tv_28)
    LeftAndRightTextView tv_28;//是否二手车

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_product_a;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {
        scrollview.setType(3);
        scrollview.setPager(2);
    }

    @Override
    public void initDatas() {
        super.initDatas();
        if (null != v) {
            ProductInfo info = v.getProductInfo();
            if (null == info) {
                isLoading = false;
                return;
            }

            ProductInfo.CustomerBean bean = info.getCustomer();
            tv_1.setRightText(bean.getBorrowerName());
            tv_2.setRightText(bean.getBorrowerAge() + "");
//            String maritalStatus = "";
//            switch (bean.getMaritalStatus()) {
//                case 0:
//                    maritalStatus = "未婚";
//                    break;
//                case 1:
//                    maritalStatus = "已婚";
//                    break;
//                case 2:
//                    maritalStatus = "离异";
//                    break;
//                case 3:
//                    maritalStatus = "丧偶";
//                    break;
//                case 4:
//                    maritalStatus = "再婚";
//                    break;
//                default:
//                    maritalStatus = "已婚";
//                    break;
//            }
            tv_3.setRightText(bean.getMaritalStatus());
            tv_4.setRightText(bean.getBorrowerEducationLevel());
            tv_5.setRightText(bean.getBorrowerJobProperty() == 0 ? "工薪" : "私营业主");
            tv_6.setRightText(bean.getBorrowerCompanyProperty());
            tv_7.setRightText(bean.getBorrowerOwnedIndustry());
            tv_8.setRightText(bean.getPositionName());
            tv_9.setRightText(CommonUtil.getMoneyGap(bean.getMonthlyIncome() + ""));
            tv_10.setRightText(bean.getPropertyType());
            tv_11.setRightText(bean.getPermanentAddress());
            tv_12.setRightText(bean.getHomeAddress());

            ProductInfo.CarInfoBean carInfo = info.getCarInfo();
            tv_21.setRightText(carInfo.getVehicleBrand());
            tv_22.setRightText(carInfo.getVehicleModels());
            tv_23.setRightText(carInfo.getBodyColor());
            tv_24.setRightText(CommonUtil.getMoneyGap("" + carInfo.getPurchasePrice()));
            tv_25.setRightText(carInfo.getTravelKilometer());
            tv_26.setRightText(carInfo.getPurchaseDate());
            tv_27.setRightText(carInfo.getAreAccident());
            tv_28.setRightText(carInfo.getIsWhetherUsedCar());
        }
    }

}
