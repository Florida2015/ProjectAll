package com.hxxc.user.app.widget;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.view.View;


public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static float MIN_SCALE = 0.55f;

    @SuppressLint("NewApi") 
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 0) { // [-1,0]
        	//A页面left
//        	position-- A页面的position 越来越大(0      -1)

            // Use the default slide transition when moving to the left page
            view.setAlpha(1);	
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);

        } else if (position <= 1) { // (0,1]
        	//B页面 right
//          B页面的position 越来越小(1       0)
            // Fade the page out.
        	//从完全看不见到可以看见的过程
            view.setAlpha(1 - position);

            // Counteract the default slide transition
            // -320 - 0
            view.setTranslationX(pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            //  private static float MIN_SCALE = 0.75f;	
            //scaleX = (1 - MIN_SCALE) * offset +  MIN_SCALE;
            //translationX = -getWidth() - getPageMargin() + offsetPixels;
            //0.75 + 0.25 * 1
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
