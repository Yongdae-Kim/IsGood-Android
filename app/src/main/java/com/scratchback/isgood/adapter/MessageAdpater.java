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
import com.scratchback.isgood.vo.Message;
import com.scratchback.isgood.vo.Review;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.techery.properratingbar.ProperRatingBar;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class MessageAdpater extends RecyclerView.Adapter<MessageAdpater.ViewHolder> {

    private Activity activity;
    private List<Message> messages;

    public MessageAdpater(Activity activity, List<Message> messages) {
        this.activity = activity;
        this.messages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_message, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Message message = messages.get(position);
        holder.messageWriterTv.setText(message.getWriter());
        holder.messageContentTv.setText(message.getContent());
        holder.messageDateTv.setText(message.getCreatedDate());
        holder.messageCv.setOnClickListener(v -> {
        });
    }

    @Override
    public int getItemCount() {
        return this.messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.message_card_view)
        CardView messageCv;
        @Bind(R.id.message_writer_tv)
        TextView messageWriterTv;
        @Bind(R.id.message_content_tv)
        TextView messageContentTv;
        @Bind(R.id.message_date_tv)
        TextView messageDateTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
