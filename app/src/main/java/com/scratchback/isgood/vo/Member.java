package com.scratchback.isgood.vo;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import com.scratchback.isgood.network.ApiConstant;

import java.io.Serializable;
import java.util.List;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class Member implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("party")
    private String party;
    @SerializedName("constituency")
    private String constituency;
    @SerializedName("rank")
    private String rank;
    @SerializedName("department")
    private String department;
    @SerializedName("address")
    private String address;
    @SerializedName("email")
    private String email;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("birth")
    private String birth;
    @SerializedName("career")
    private String career;
    @SerializedName("military")
    private String military;
    @SerializedName("crime")
    private String crime;
    @SerializedName("image")
    private String image;

    @SerializedName("main_session_attendance")
    private String mainSessionAttendance;
    @SerializedName("sub_session_attendance")
    private String subSessionAttendance;
    @SerializedName("bill_proposal")
    private String billProposal;

    @SerializedName("reviews_count")
    private int reviewsCnt;
    @SerializedName("reviews_grade_average")

    private double reviewsGradeAvg;
    @SerializedName("element_stats_count")
    private int elementStatsCnt;
    @SerializedName("element1")
    private Element element1;
    @SerializedName("element2")
    private Element element2;
    @SerializedName("element3")
    private Element element3;
    @SerializedName("element4")
    private Element element4;
    @SerializedName("element5")
    private Element element5;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParty() {
        return party;
    }

    public String getConstituency() {
        return constituency;
    }

    public String getRank() {
        return rank;
    }

    public String getDepartment() {
        return department;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getBirth() {
        return birth;
    }

    public String getCareer() {
        return career;
    }

    public String getMilitary() {
        return military;
    }

    public String getCrime() {
        return crime;
    }

    public String getImage() {
        return ApiConstant.URI_REQUEST_ROOT + image;
    }


    public String getMainSessionAttendance() {
        return mainSessionAttendance;
    }

    public String getSubSessionAttendance() {
        return subSessionAttendance;
    }

    public String getBillProposal() {
        return billProposal;
    }

    public int getReviewsCnt() {
        return reviewsCnt;
    }

    public double getReviewsGradeAvg() {
        return reviewsGradeAvg;
    }


    public int getElementStatsCnt() {
        return elementStatsCnt;
    }

    public Element getElement1() {
        return element1;
    }

    public Element getElement2() {
        return element2;
    }

    public Element getElement3() {
        return element3;
    }

    public Element getElement4() {
        return element4;
    }

    public Element getElement5() {
        return element5;
    }

    public List<Element> getElements() {
        List<Element> elements = Lists.newArrayList();
        elements.add(getElement1());
        elements.add(getElement2());
        elements.add(getElement3());
        elements.add(getElement4());
        elements.add(getElement5());
        return elements;
    }

    public String getReviewsInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("평점 : ").append(getReviewsGradeAvg()).append(" / 갯수 : ").append(getReviewsCnt());
        return sb.toString();
    }

    public String getBelong() {
        StringBuilder sb = new StringBuilder();
        sb.append(getParty()).append(" / ").append(getConstituency()).append(" / ").append(getRank());
        return sb.toString();
    }
}
