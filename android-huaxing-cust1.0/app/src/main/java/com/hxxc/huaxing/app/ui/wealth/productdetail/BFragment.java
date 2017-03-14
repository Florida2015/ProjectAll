package com.hxxc.huaxing.app.ui.wealth.productdetail;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.hxxc.huaxing.app.PicassoRoundTransform;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.ProductInfo;
import com.hxxc.huaxing.app.ui.wealth.BaseFragment2;
import com.hxxc.huaxing.app.wedgit.verticalpager.CustScrollView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenqun on 2016/9/23.
 */

public class BFragment extends BaseFragment2 {
    @BindView(R.id.scrollview)
    CustScrollView scrollview;

    @BindView(R.id.iv_car)
    ImageView iv_car;
    @BindView(R.id.iv_identity1)
    ImageView iv_identity1;
    @BindView(R.id.iv_identity2)
    ImageView iv_identity2;
    @BindView(R.id.iv_car2)
    ImageView iv_car2;
    private ProductInfo.PhotosBean photosBean;

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_product_b;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {
        scrollview.setType(3);
        scrollview.setPager(2);
    }

    @OnClick({R.id.iv_car, R.id.iv_identity1, R.id.iv_identity2, R.id.iv_car2})
    public void onClick(View view) {
        int index = 0;
        switch (view.getId()) {
            case R.id.iv_car:
                index = 0;
                break;
            case R.id.iv_identity1:
                index = 1;
                break;
            case R.id.iv_identity2:
                index = 2;
                break;
            case R.id.iv_car2:
                index = 3;
                break;
        }

        Intent intent = new Intent(getActivity(), PhotoActivity.class);
        intent.putStringArrayListExtra("list", mPhotos);
        intent.putExtra("position", index);
        startActivity(intent);
    }

    private ArrayList<String> mPhotos = new ArrayList<>();

    private void setDatas() {
        if (scrollview != null) {
            scrollview.post(new Runnable() {
                @Override
                public void run() {
                    Picasso.with(mContext).load(photosBean.getCarUrl()).fit().error(R.mipmap.error).placeholder(R.mipmap.error).transform(new PicassoRoundTransform()).into(iv_car);
                    Picasso.with(mContext).load(photosBean.getDrivingLicenseUrl()).fit().error(R.mipmap.error).placeholder(R.mipmap.error).transform(new PicassoRoundTransform()).into(iv_identity1);
                    Picasso.with(mContext).load(photosBean.getIdurl()).fit().error(R.mipmap.error).placeholder(R.mipmap.error).transform(new PicassoRoundTransform()).into(iv_identity2);
                    Picasso.with(mContext).load(photosBean.getEvhiclesRregisteredUrl()).fit().error(R.mipmap.error).placeholder(R.mipmap.error).transform(new PicassoRoundTransform()).into(iv_car2);
                }
            });
        }
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

            photosBean = info.getPhotos();
            mPhotos.clear();
            mPhotos.add(photosBean.getCarUrl());
            mPhotos.add(photosBean.getDrivingLicenseUrl());
            mPhotos.add(photosBean.getIdurl());
            mPhotos.add(photosBean.getEvhiclesRregisteredUrl());
            setDatas();
        }
    }
}
