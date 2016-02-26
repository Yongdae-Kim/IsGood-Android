package com.scratchback.isgood.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by useruser on 16. 2. 17..
 */
public class Message {
    @SerializedName("id")
    private int id;
    @SerializedName("review_id")
    private int reviewId;
    @SerializedName("writer")
    private String writer;
    @SerializedName("content")
    private String content;
    @SerializedName("created_at")
    private String createdDate;
    @SerializedName("updated_at")
    private String updatedDate;

    public int getId() {
        return id;
    }

    public int getReviewId() {
        return reviewId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }
}
