package com.scratchback.isgood.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scratchback.isgood.R;
import com.scratchback.isgood.activity.MainActivity;
import com.scratchback.isgood.activity.MemberDetailActivity;
import com.scratchback.isgood.vo.Member;
import com.scratchback.isgood.vo.MyMember;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class FavoriteAdpater extends RecyclerView.Adapter<FavoriteAdpater.ViewHolder> {

    private Activity activity;
    private List<MyMember> myMembers;

    public FavoriteAdpater(Activity activity, List<MyMember> myMembers) {
        this.activity = activity;
        this.myMembers = myMembers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_favorite, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyMember myMember = myMembers.get(position);
        loadAdThumbnail(holder, myMember);
        holder.favoriteMemberNameTv.setText(myMember.getName());
        holder.favoriteMemberBelongTv.setText(myMember.getBelong());
        holder.favoriteMemberDepartmentTv.setText(myMember.getDepartment());
        holder.myMemberCv.setOnClickListener(v -> {
            Intent intent = new Intent(activity, MemberDetailActivity.class);
            intent.putExtra(MainActivity.EXTRA_MEMBER_ID, myMember.getMemberId());
            activity.startActivity(intent);
        });
    }

    private void loadAdThumbnail(final ViewHolder holder, MyMember myMember) {
        String imgUri = myMember.getImage();
        holder.favoriteMemberIv.setTag(imgUri);

        if (imgUri.equals(holder.favoriteMemberIv.getTag())) {
            Picasso.with(activity)
                    .load(imgUri)
                    .into(holder.favoriteMemberIv);
        }
    }

    @Override
    public int getItemCount() {
        return this.myMembers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.my_member_card_view)
        CardView myMemberCv;
        @Bind(R.id.favorite_member_iv)
        ImageView favoriteMemberIv;
        @Bind(R.id.favorite_member_name_tv)
        TextView favoriteMemberNameTv;
        @Bind(R.id.favorite_member_belong_tv)
        TextView favoriteMemberBelongTv;
        @Bind(R.id.favorite_member_department_tv)
        TextView favoriteMemberDepartmentTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
