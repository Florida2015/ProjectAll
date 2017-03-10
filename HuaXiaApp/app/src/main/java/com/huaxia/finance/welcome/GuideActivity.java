package com.huaxia.finance.welcome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.framwork.Utils.SharedPreferencesUtils;
import com.huaxia.finance.MenuTwoActivity;
import com.huaxia.finance.R;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：引导页
 * 
 * @author houwen.lai 2014.10.31 17:09
 */
public class GuideActivity extends Activity implements OnClickListener,
		OnPageChangeListener {
	private final String mPageName = GuideActivity.class.getSimpleName();

	private ViewPager viewpager;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;

	// 底部小店图片
	private ImageView dots_1;
	private ImageView dots_2;
	private ImageView dots_3;
	private ImageView dots_4;
	// 按钮
	private Button imgbtn;

	// 底部小图片
	private int currentIndex;
	//
	private View linear_pager_1;
	private View linear_pager_2;
	private View linear_pager_3;
	private View linear_pager_4;
	//
	private Context mContext;

	private ImageButton img_btn_skip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_view);
		mContext = this;
		//
		setGuidePic();
		//

	}

	// 初始化引导页
	public void setGuidePic() {
		views = new ArrayList<View>();
		// 底部图片
		dots_1 = (ImageView) findViewById(R.id.img_dot_1);
		dots_2 = (ImageView) findViewById(R.id.img_dot_2);
		dots_3 = (ImageView) findViewById(R.id.img_dot_3);
		dots_4 = (ImageView) findViewById(R.id.img_dot_4);
		img_btn_skip = (ImageButton) findViewById(R.id.img_btn_skip);
		img_btn_skip.setOnClickListener(this);

		// viewpager
		viewpager = (ViewPager) findViewById(R.id.guide_view_page);
		LayoutInflater layoutInflater = getLayoutInflater();
		linear_pager_1 = layoutInflater.inflate(R.layout.guide_view_pager_1,
				null);
		linear_pager_2 = layoutInflater.inflate(R.layout.guide_view_pager_2,
				null);
		linear_pager_3 = layoutInflater.inflate(R.layout.guide_view_pager_3,
				null);
		linear_pager_4 = layoutInflater.inflate(R.layout.guide_view_pager_4,
				null);
		//
		imgbtn = (Button) linear_pager_4.findViewById(R.id.img_btn_dots);
		imgbtn.setOnClickListener(this);
		//
		views.add(linear_pager_1);
		views.add(linear_pager_2);
		views.add(linear_pager_3);
		views.add(linear_pager_4);
		// // 初始化Adapter
		vpAdapter = new ViewPagerAdapter(views);
		viewpager.setAdapter(vpAdapter);
		viewpager.setOnPageChangeListener(this);

		// 初始化底部小点
		initDots(0);

	}

	private void initDots(int index) {
		if (views == null || views.size() < index + 1) {
			return;
		}
//		dots_1.setEnabled(false);
//		dots_2.setEnabled(false);
//		dots_3.setEnabled(false);
//		dots_4.setEnabled(false);
		// 循环取得小点图片
		switch (index) {
		case 0:
			dots_1.setEnabled(true);// 设置为白色，即选中状态
			break;
		case 1:
			dots_2.setEnabled(true);
			break;
		case 2:
			dots_3.setEnabled(true);
			break;
		case 3:
			dots_4.setEnabled(true);
			break;

		default:
			break;
		}
		currentIndex = index;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		initDots(arg0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.img_btn_skip:
			case R.id.img_btn_dots:// 进入主界面
				//
	//			Toast.makeText(mContext, "进入主界面", Toast.LENGTH_SHORT).show();

				SharedPreferencesUtils.getInstanse().putFirstUser(mContext,true);//
				startActivity(new Intent(GuideActivity.this,MenuTwoActivity.class));// 进入主界面
				this.finish();
				overridePendingTransition(R.anim.fade_in, R.anim.hold);
				break;
			default:
				break;
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

}
