package com.hxxc.user.app.widget.piechart_2;

/**
 * Created by inf on 2016/11/29.
 */

public class TestEntity implements IPieElement{

    private float mValue;
    private String mColor;

    public TestEntity(float value,String color){
        mValue=value;
        mColor=color;
    }
    @Override
    public float getValue() {
        return mValue;
    }

    @Override
    public String getColor() {
        return mColor;
    }
}
