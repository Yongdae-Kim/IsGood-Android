package com.scratchback.isgood.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by useruser on 16. 2. 17..
 */
public class Avatar {
    @SerializedName("id")
    private int id;
    @SerializedName("nickname")
    private String nickname;

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
}
