package com.framwork.adsviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 *
 * 测试广告
 * Created by wwt on 2015/1/15.
 */
public class TestAdsActivity extends Activity implements View.OnClickListener{

    private String[] picUrls = new String[] {
            "http://www.w5t5.com/ads/20150331/Ad-1.png",
            "http://www.w5t5.com/ads/20150331/Ad-2.png",
            "http://www.w5t5.com/ads/20150331/Ad-3.png",
            "http://www.w5t5.com/ads/20150331/Ad-4.png",
            "http://www.w5t5.com/ads/20150331/Ad-3.png",
            "http://www.w5t5.com/ads/20150331/Ad-3.png",
            "http://www.w5t5.com/ads/20150331/Ad-2.png",
            "http://www.w5t5.com/ads/20150331/Ad-1.png" };

//    int[] picDreadles = new int[] { R.drawable.welcome_1, R.drawable.welcome_2,
//            R.drawable.welcome_3, R.drawable.welcome_4,R.drawable.welcome_3,
//            R.drawable.welcome_2,R.drawable.welcome_1,R.drawable.welcome_4 };

    ImageView imageView;
    Advertising ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test_ads_activity);
//        ll = (Advertising) findViewById(R.id.ads_activity);
//        ll.setAdsClickLister(new Advertising.OnAdsClikLister() {
//            @Override
//            public void OnAdsClikLister(View view, int index) {
//                ToastUtils.showLong("view__" + index);
//            }
//        });
    }

    public void adsInit() {

//		imageView = (ImageView) findViewById(R.id.img_view);
//		String url = "http://ope.w5t5.com/1.1/img/ksmhzp.jpg";

        // Bitmap tmpBitmap = null;
        // try {
        // InputStream is = new java.net.URL(url).openStream();
        // tmpBitmap = BitmapFactory.decodeStream(is);
        // is.close();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // imageView.setImageBitmap(tmpBitmap);

//        new ImageLoader(this).DisplayImage(url,imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_R_8:
//                ll.setAdsPicArraysUrl(new String[] {
//                        "http://www.w5t5.com/ads/20150331/Ad-1.png",
//                        "http://www.w5t5.com/ads/20150331/Ad-2.png",
//                        "http://www.w5t5.com/ads/20150331/Ad-3.png",
//                        "http://www.w5t5.com/ads/20150331/Ad-4.png",
//                        "http://www.w5t5.com/ads/20150331/Ad-3.png",
//                        "http://www.w5t5.com/ads/20150331/Ad-3.png",
//                        "http://www.w5t5.com/ads/20150331/Ad-2.png",
//                        "http://www.w5t5.com/ads/20150331/Ad-1.png" });
//                break;
        }
    }

}
