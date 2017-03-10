package com.framwork.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaxia.finance.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * 
 * 
 * @author MichaelYe
 * @since 2012-9-5
 * */
public class BottomBar extends LinearLayout implements OnClickListener {

	private static final int TAG_0 = 0;
	private static final int TAG_1 = 1;
	private static final int TAG_2 = 2;
	private static final int TAG_3 = 3;
	private static final int TAG_4 = 4;
	private Context mContext;
	private TextView tvOne;

	public BottomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	public BottomBar(Context context) {
		super(context);
		mContext = context;
		init();
	}

	private List<View> itemList;

    private Button btnOne;
    private Button btnTwo;
    private Button btnThree;
    private Button btnFour;
    private Button btnFive;

	/**
	 * get the buttons from layout
	 * 
	 *
	 * 
	 * */
	private void init() {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.bottom_bar, null);
		layout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f));
		tvOne = (TextView) layout.findViewById(R.id.tv_warming);
		btnOne = (Button) layout.findViewById(R.id.btn_item_one);
		btnTwo = (Button) layout.findViewById(R.id.btn_item_two);
		btnThree = (Button) layout.findViewById(R.id.btn_item_three);
		btnFour = (Button) layout.findViewById(R.id.btn_item_four);
		btnFive = (Button) layout.findViewById(R.id.btn_item_five);
		btnOne.setOnClickListener(this);
		btnTwo.setOnClickListener(this);
		btnThree.setOnClickListener(this);
		btnFour.setOnClickListener(this);
		btnFive.setOnClickListener(this);
		btnOne.setTag(TAG_0);
		btnTwo.setTag(TAG_1);
		btnThree.setTag(TAG_2);
		btnFour.setTag(TAG_3);
		btnFive.setTag(TAG_4);
		itemList = new ArrayList<View>();
		itemList.add(btnOne);
		itemList.add(btnTwo);
		itemList.add(btnThree);
		itemList.add(btnFour);
		itemList.add(btnFive);
		this.addView(layout);
	}

	@Override
	public void onClick(View v) {
		int tag = (Integer) v.getTag();
        setButtomBarId(tag);
//		switch (tag) {
//		case TAG_0:
//			setNormalState(lastButton);
//			setSelectedState(tag);
//			break;
//		case TAG_1:
//			setNormalState(lastButton);
//			setSelectedState(tag);
//			break;
//		case TAG_2:
//			setNormalState(lastButton);
//			setSelectedState(tag);
//			break;
//		case TAG_3:
//			setNormalState(lastButton);
//			setSelectedState(tag);
//			break;
//		case TAG_4:
//			setNormalState(lastButton);
//			setSelectedState(tag);
//			break;
//		}
	}

    /**
     *
     * @param index
     */
    public void setButtomBarId(int index){
        btnOne.setSelected(false);
        btnTwo.setSelected(false);
        btnThree.setSelected(false);
        btnFour.setSelected(false);
        btnFive.setSelected(false);
        switch (index){
            case 0:
                btnOne.setSelected(true);
                break;
            case 1:
                btnTwo.setSelected(true);
                break;
            case 2:
                btnThree.setSelected(true);
                break;
            case 3:
                btnFour.setSelected(true);
                break;
            case 4:
                btnFive.setSelected(true);
                break;
        }
        onItemChangedListener.onItemChanged(index);
    }

	private int lastButton = -1;

	/**
	 * set the default bar item of selected
	 * 
	 * ����Ĭ��ѡ�е�Item
	 * 
	 * */
	public void setSelectedState(int index) {
		setNormalState(lastButton);// //
		if (index != -1 && onItemChangedListener != null) {
			if (index > itemList.size()) {
				throw new RuntimeException(
						"the value of default bar item can not bigger than string array's length");
			}
			itemList.get(index).setSelected(true);
			onItemChangedListener.onItemChanged(index);
			lastButton = index;
		}
	}

	/**
	 * set the normal state of the button by given index
	 * 
	 * �ָ�δѡ��״̬
	 * 
	 * */
	private void setNormalState(int index) {
		if (index != -1) {
			if (index > itemList.size()) {
				throw new RuntimeException(
						"the value of default bar item can not bigger than string array's length");
			}
			itemList.get(index).setSelected(false);
		}
	}

	/**
	 * make the red indicate VISIBLE
	 * 
	 * ������ִ�а�ť���Ͻǵĺ�ɫСͼ��Ŀɼ�
	 * 
	 * */
	public void showIndicate(int value) {
		tvOne.setText(value + "");
		tvOne.setVisibility(View.VISIBLE);
	}

	/**
	 * make the red indicate GONE
	 * 
	 * ���� ��ִ�а�ť���Ͻǵĺ�ɫСͼ��
	 * 
	 * */
	public void hideIndicate() {
		tvOne.setVisibility(View.GONE);
	}

	public interface OnItemChangedListener {
		public void onItemChanged(int index);
	}

	private OnItemChangedListener onItemChangedListener;

	public void setOnItemChangedListener(
			OnItemChangedListener onItemChangedListener) {
		this.onItemChangedListener = onItemChangedListener;
	}
}
