package com.scratchback.isgood.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by useruser on 16. 2. 19..
 */
public class Chat {

    private boolean isMine;
    @SerializedName("username")
    private String nickname;
    @SerializedName("message")
    private String message;

    public Chat() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMessage() {
        return message;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }
}
