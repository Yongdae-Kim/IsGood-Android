package com.scratchback.isgood.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scratchback.isgood.R;
import com.scratchback.isgood.activity.MainActivity;
import com.scratchback.isgood.activity.MemberDetailActivity;
import com.scratchback.isgood.adapter.ReviewAdpater;
import com.scratchback.isgood.listener.EndlessRecyclerViewScrollListener;
import com.scratchback.isgood.network.ApiConstant;
import com.scratchback.isgood.network.ApiRequester;
import com.scratchback.isgood.network.ApiService;
import com.scratchback.isgood.preferences.AvatarPreferencesManager;
import com.scratchback.isgood.util.ToastSystem;
import com.scratchback.isgood.vo.Review;
import com.scratchback.isgood.vo.Reviews;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.techery.properratingbar.ProperRatingBar;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class MemberReviewFragment extends Fragment {

    public static final String EXTRA_MEMBER_ID = MainActivity.EXTRA_MEMBER_ID;
    public static final String EXTRA_REVIEW_ID = "EXTRA_REVIEW_ID";
    private static final int DEFAULT_RATING = 3;

    private View view;

    @Bind(R.id.review_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.register_review_rating_bar)
    ProperRatingBar registerRatingBar;
    @Bind(R.id.register_review_content_tv)
    TextView registerContentTv;

    private int memberId;

    private boolean isRequesting = false;

    private ReviewAdpater reviewAdpater;
    private List<Review> reviewList;

    public MemberReviewFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_member_review, container, false);
        }
        ButterKnife.bind(this, view);
        initView();

        return view;
    }

    @Override
    public void onResume() {
        requestReviewList(memberId, ApiConstant.REQUEST_FIRST_PAGE);
        super.onResume();
    }

    private void initView() {
        initRecyclerView();

        Bundle args = getArguments();
        if (args != null) {
            memberId = args.getInt(MemberDetailActivity.ARGUMENT_MEMBER_ID);
            requestReviewList(memberId, ApiConstant.REQUEST_FIRST_PAGE);
        }
    }


    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
//                if (!isRequesting) {
//                    requestReviewList(memberId, page);
//                }
                return true;
            }
        });
    }

    private void requestReviewList(int memberId, int page) {
        isRequesting = true;

        ApiService service = ApiRequester.getInstacne().getService();
        service.getReviews(memberId, page).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Response<Reviews> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Reviews reviews = response.body();
                    reviewList = reviews.getRows();
                    reviewAdpater = new ReviewAdpater(getActivity(), reviewList);
                    recyclerView.setAdapter(reviewAdpater);
//                    if (reviewList == null) {
//                        reviewList = reviews.getRows();
//                        reviewAdpater = new ReviewAdpater(getActivity(), reviewList);
//                        recyclerView.setAdapter(reviewAdpater);
//                    } else {
//                        if (reviews.getPage() <= reviews.getTotal()) {
//                            reviewList.addAll(reviews.getRows());
//                            reviewAdpater.notifyDataSetChanged();
//                        }
//                    }
                    isRequesting = false;
                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {
                isRequesting = false;
            }
        });
    }

    @OnClick(R.id.register_review_btn)
    public void registerReview() {
        ApiService service = ApiRequester.getInstacne().getService();
        int avatarId = AvatarPreferencesManager.getInstacne(getActivity()).get().getId();
        int rating = registerRatingBar.getRating();
        String content = registerContentTv.getText().toString();
        if (registerContentTv.getText().toString().trim().length() == 0) {
            registerContentTv.requestFocus();
            ToastSystem.getInstacne().show(getActivity(), ToastSystem.NO_CONTENT);
            return;
        }

        service.registerReview(memberId, avatarId, rating, content)
                .enqueue(new Callback<Review>() {
                    @Override
                    public void onResponse(Response<Review> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            initRegisterForm();
                            requestReviewList(memberId, ApiConstant.REQUEST_FIRST_PAGE);
                            recyclerView.scrollToPosition(reviewAdpater.getItemCount() - 1);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
    }

    private void initRegisterForm() {
        registerRatingBar.setRating(DEFAULT_RATING);
        registerContentTv.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
