package com.scratchback.isgood.network;

/**
 * Created by useruser on 2016. 2. 12..
 */
public class ApiConstant {
    public static final int REQUEST_FIRST_PAGE = 1;
    // API 루트 주소
    public static final String URI_REQUEST_ROOT = "http://10.10.1.147:9090";
    // API 요청 주소
    static final String URI_REQUEST_API = URI_REQUEST_ROOT + "/api/";
    // 아바타 등록
    static final String URI_POST_REGISTER_AVATAR = "avatars.json";
    // 정당목록
    static final String URI_GET_PARTY_LIST = "parties.json";
    // 지역목록
    static final String URI_GET_REGION_LIST = "regions.json";
    // 국회의원 자동완성
    static final String URI_GET_MEMBERS_AUTOCOMPLETE = "members_autocomplete.json";
    // 국회의원 목록
    static final String URI_GET_MEMBERS = "members.json";
    // 국회의원
    static final String URI_GET_MEMBER = "members/{id}.json";
    // 리뷰 목록
    static final String URI_GET_REVIEWS = "members/{member_id}/reviews.json";
    // 리뷰 등록
    static final String URI_POST_REGISTER_REVIEW = URI_GET_REVIEWS;
    // 메시지 목록
    static final String URI_GET_MESSAGES = "members/{member_id}/reviews/{review_id}/messages.json";
    // 메시지 등록
    static final String URI_POST_REGISTER_MESSAGE = URI_GET_MESSAGES;
}

