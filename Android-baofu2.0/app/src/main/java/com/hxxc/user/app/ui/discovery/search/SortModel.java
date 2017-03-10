package com.hxxc.user.app.ui.discovery.search;

public class SortModel implements Comparable<SortModel>{

    private String name;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    @Override
    public String toString() {
        return "SortModel{" +
                "name='" + name + '\'' +
                ", sortLetters='" + sortLetters + '\'' +
                '}';
    }


    @Override
    public int compareTo(SortModel another) {
        if (this.getSortLetters().equals("@")
                || another.getSortLetters().equals("#")) {
            return -1;
        } else if (this.getSortLetters().equals("#")
                || another.getSortLetters().equals("@")) {
            return 1;
        } else {
            return this.getSortLetters().compareTo(another.getSortLetters());
        }
    }
}

