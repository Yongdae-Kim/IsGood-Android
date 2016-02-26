package com.scratchback.isgood.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by useruser on 2016. 2. 14..
 */
public class Element implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("point_average")
    private int pointAvg;

    public String getName() {
        return name;
    }

    public int getPointAvg() {
        return pointAvg;
    }

    public String getSpecifiedName() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName()).append("(").append(getPointAvg()).append(")");
        return sb.toString();
    }
}
