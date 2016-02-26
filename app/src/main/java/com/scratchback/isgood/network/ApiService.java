package com.scratchback.isgood.network;

import com.scratchback.isgood.vo.Avatar;
import com.scratchback.isgood.vo.Member;
import com.scratchback.isgood.vo.Members;
import com.scratchback.isgood.vo.Message;
import com.scratchback.isgood.vo.Party;
import com.scratchback.isgood.vo.Region;
import com.scratchback.isgood.vo.Review;
import com.scratchback.isgood.vo.Reviews;

import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by useruser on 2016. 2. 11..
 */
public interface ApiService {

    @POST(ApiConstant.URI_POST_REGISTER_AVATAR)
    Call<Avatar> registerAvatar();

    @GET(ApiConstant.URI_GET_PARTY_LIST)
    Call<List<Party>> getPartyList();

    @GET(ApiConstant.URI_GET_REGION_LIST)
    Call<List<Region>> getRegionList();

    @GET(ApiConstant.URI_GET_MEMBERS_AUTOCOMPLETE)
    Call<List<Member>> getMembersAutocomplete();

    @GET(ApiConstant.URI_GET_MEMBERS)
    Call<Members> getMembers(@Query("page") int page, @QueryMap Map<String, String> options);

    @GET(ApiConstant.URI_GET_MEMBER)
    Call<Member> getMember(@Path("id") int id);

    @GET(ApiConstant.URI_GET_REVIEWS)
    Call<Reviews> getReviews(@Path("member_id") int memberId, @Query("page") int page);

    @FormUrlEncoded
    @POST(ApiConstant.URI_POST_REGISTER_REVIEW)
    Call<Review> registerReview(
            @Path("member_id") int memberId,
            @Field("avatar_id") int avatarId,
            @Field("grade") double grade,
            @Field("content") String content
    );

    @GET(ApiConstant.URI_GET_MESSAGES)
    Call<List<Message>> getMessages(@Path("member_id") int memberId, @Path("review_id") int reviewId);

    @FormUrlEncoded
    @POST(ApiConstant.URI_POST_REGISTER_MESSAGE)
    Call<Message> registerMessage(
            @Path("member_id") int memberId,
            @Path("review_id") int reviewIdPath,
            @Field("review_id") int reviewIdField,
            @Field("avatar_id") int avatarId,
            @Field("content") String content
    );
}
