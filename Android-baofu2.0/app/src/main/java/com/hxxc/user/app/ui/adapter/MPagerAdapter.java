package com.hxxc.user.app.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class MPagerAdapter extends PagerAdapter {
	
	public MPagerAdapter(List<ImageView> viewList){
		this.viewList = viewList;
	}
	
	private List<ImageView> viewList;//view数组
	
	@Override  
    public boolean isViewFromObject(View arg0, Object arg1) {  
        return arg0 == arg1;  
    }  
      
    @Override  
    public int getCount() {  
        return viewList.size();  
    }  
      
    @Override  
    public void destroyItem(ViewGroup container, int position,  
            Object object) {  
        container.removeView(viewList.get(position));  
    }  
      
    @Override  
    public Object instantiateItem(ViewGroup container, int position) {  
        container.addView(viewList.get(position));  
        return viewList.get(position);  
    }  
}
