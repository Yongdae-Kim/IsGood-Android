package com.scratchback.isgood.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by useruser on 2016. 2. 14..
 */
public class Review implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("member_id")
    private int member_id;
    @SerializedName("writer")
    private String writer;
    @SerializedName("content")
    private String content;
    @SerializedName("grade")
    private double grade;
    @SerializedName("messages_count")
    private int messagesCnt;
    @SerializedName("created_at")
    private String createdDate;
    @SerializedName("updated_at")
    private String updatedDate;

    public int getId() {
        return id;
    }

    public int getMember_id() {
        return member_id;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public double getGrade() {
        return grade;
    }

    public int getMessagesCnt() {
        return messagesCnt;
    }

    public String getCreatedDate() {
        return createdDate;
    }


    public String getUpdatedDate() {
        return updatedDate;
    }


}

