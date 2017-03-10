package com.hxxc.user.app.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenqun on 2016/6/27.
 */
public class DatasConfig {

    public static final String[] MONTH = new String[]{"01", "02", "03", "04", "05", "06", "07", "08","09","10","11","12"};
    public static final List<String> YEAR = new ArrayList<String>();
    public static final List<String> DAY = new ArrayList<String>();

    static{
        for (int i = 1920; i < 2017; i++) {
            YEAR.add(i+"");
        }
        for (int i = 1; i <= 31; i++) {
            if (i<10) {
                DAY.add("0"+i+"");
            }else {
                DAY.add(i+"");
            }
        }
    }
}
