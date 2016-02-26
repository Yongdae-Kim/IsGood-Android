package com.scratchback.isgood.vo;

import com.orm.SugarRecord;

/**
 * Created by useruser on 16. 2. 18..
 */
public class MyMember extends SugarRecord {
    private int memberId;
    private String party;
    private String belong;
    private String name;
    private String image;
    private String department;

    public MyMember() {
        super();
    }

    public MyMember(Member member) {
        super();
        this.memberId = member.getId();
        this.party = member.getParty();
        this.belong = member.getBelong();
        this.name = member.getName();
        this.image = member.getImage();
        this.department = member.getDepartment();
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getParty() {
        return party;
    }

    public String getBelong() {
        return belong;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;

        if (object != null && object instanceof MyMember) {
            isEqual = (this.getMemberId() == ((MyMember) object).getMemberId());
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return this.getMemberId();
    }

    @Override
    public String toString() {
        return "MyMember{" +
                "id=" + getId() +
                ", memberId=" + memberId + '\'' +
                ", party='" + party + '\'' +
                ", belong='" + belong + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
