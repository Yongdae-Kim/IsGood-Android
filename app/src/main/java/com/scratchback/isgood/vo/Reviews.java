package com.scratchback.isgood.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by useruser on 2016. 2. 12..
 */
public class Reviews implements Serializable {
    @SerializedName("page")
    private int page;
    @SerializedName("total")
    private int total;
    @SerializedName("records")
    private int records;
    @SerializedName("rows")
    private List<Review> rows;

    public int getPage() {
        return page;
    }

    public int getTotal() {
        return total;
    }

    public int getRecords() {
        return records;
    }

    public List<Review> getRows() {
        return rows;
    }
}
