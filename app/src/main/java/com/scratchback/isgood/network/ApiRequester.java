package com.scratchback.isgood.network;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class ApiRequester {

    private static ApiRequester requester;

    private Retrofit retrofit;

    private ApiRequester() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstant.URI_REQUEST_API).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static ApiRequester getInstacne() {
        if (requester == null) {
            requester = new ApiRequester();
        }
        return requester;
    }

    public ApiService getService() {
        return retrofit.create(ApiService.class);
    }
}

