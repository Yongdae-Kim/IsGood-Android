package com.scratchback.isgood.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class Party implements CommonCode, Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
