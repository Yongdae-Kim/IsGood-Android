package com.scratchback.isgood.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.scratchback.isgood.R;
import com.scratchback.isgood.vo.Chat;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class ChatAdpater extends RecyclerView.Adapter<ChatAdpater.ViewHolder> {

    private Activity activity;
    private List<Chat> chats;

    public ChatAdpater(Activity activity, List<Chat> chats) {
        this.activity = activity;
        this.chats = chats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_chat, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Chat chat = chats.get(position);
        setAlignment(holder, chat.isMine());
        holder.chatMessageTv.setText(chat.getMessage());
        holder.chatNicknameTv.setText(chat.getNickname());
    }

    @Override
    public int getItemCount() {
        return this.chats.size();
    }

    private void setAlignment(ViewHolder holder, boolean isMe) {
        if (!isMe) {
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBackground.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.contentWithBackground.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.chatMessageTv.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.chatMessageTv.setLayoutParams(layoutParams);
            layoutParams = (LinearLayout.LayoutParams) holder.chatNicknameTv.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.chatNicknameTv.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
        } else {
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBackground.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.contentWithBackground.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.chatMessageTv.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.chatMessageTv.setLayoutParams(layoutParams);
            layoutParams = (LinearLayout.LayoutParams) holder.chatNicknameTv.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.chatNicknameTv.setBootstrapBrand(DefaultBootstrapBrand.INFO);

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.chat_message_tv)
        TextView chatMessageTv;
        @Bind(R.id.chat_nickname_tv)
        BootstrapLabel chatNicknameTv;

        @Bind(R.id.content)
        LinearLayout content;
        @Bind(R.id.content_with_background)
        LinearLayout contentWithBackground;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
