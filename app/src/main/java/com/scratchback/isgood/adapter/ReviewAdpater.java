package com.scratchback.isgood.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scratchback.isgood.R;
import com.scratchback.isgood.activity.ReviewMessageActivity;
import com.scratchback.isgood.fragment.MemberReviewFragment;
import com.scratchback.isgood.vo.Review;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.techery.properratingbar.ProperRatingBar;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class ReviewAdpater extends RecyclerView.Adapter<ReviewAdpater.ViewHolder> {

    private Activity activity;
    private List<Review> reviews;

    public ReviewAdpater(Activity activity, List<Review> reviews) {
        this.activity = activity;
        this.reviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_review, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Review review = reviews.get(position);
        holder.reviewWriterTv.setText(review.getWriter());
        holder.reviewRatingBar.setRating((int) Math.round(review.getGrade()));
        holder.reviewContentTv.setText(review.getContent());
        holder.reviewDateTv.setText(review.getCreatedDate());
        holder.reviewCv.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ReviewMessageActivity.class);
            intent.putExtra(MemberReviewFragment.EXTRA_MEMBER_ID, review.getMember_id());
            intent.putExtra(MemberReviewFragment.EXTRA_REVIEW_ID, review.getId());
            activity.startActivity(intent);
        });
        showMessageCnt(holder.reviewMsgCntTv, review);
    }

    public void showMessageCnt(TextView tv, Review review) {
        int msgCnt = review.getMessagesCnt();
        if (msgCnt > 0) {
            if (msgCnt > 9) {
                tv.setText("9+");
            } else {
                tv.setText(String.valueOf(msgCnt));
            }
        } else {
            tv.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.review_card_view)
        CardView reviewCv;
        @Bind(R.id.review_writer_tv)
        TextView reviewWriterTv;
        @Bind(R.id.review_rating_bar)
        ProperRatingBar reviewRatingBar;
        @Bind(R.id.review_content_tv)
        TextView reviewContentTv;
        @Bind(R.id.review_date_tv)
        TextView reviewDateTv;
        @Bind(R.id.review_msg_cnt_tv)
        TextView reviewMsgCntTv;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
