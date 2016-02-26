package com.scratchback.isgood.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.scratchback.isgood.R;
import com.scratchback.isgood.activity.MainActivity;
import com.scratchback.isgood.activity.MemberDetailActivity;
import com.scratchback.isgood.vo.Member;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class MemberAdpater extends RecyclerView.Adapter<MemberAdpater.ViewHolder> {

    private Activity activity;
    private List<Member> members;

    public MemberAdpater(Activity activity, List<Member> members) {
        this.activity = activity;
        this.members = members;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_member, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Member member = members.get(position);
        loadAdThumbnail(holder, member);
        holder.memberNameTv.setText(member.getName());
        holder.memberBelongTv.setText(member.getBelong());
        holder.memberDepartmentTv.setText(member.getDepartment());
        holder.memberReviewsInfoTv.setText(member.getReviewsInfo());
        holder.memberCv.setOnClickListener(v -> {
            Intent intent = new Intent(activity, MemberDetailActivity.class);
            intent.putExtra(MainActivity.EXTRA_MEMBER_ID, member.getId());
            activity.startActivity(intent);
        });
    }

    private void loadAdThumbnail(final ViewHolder holder, Member member) {
        String imgUri = member.getImage();
        holder.memberIv.setTag(imgUri);

        if (imgUri.equals(holder.memberIv.getTag())) {
            Picasso.with(activity)
                    .load(imgUri)
                    .into(holder.memberIv);
        }
    }

    @Override
    public int getItemCount() {
        return this.members.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.member_card_view)
        CardView memberCv;
        @Bind(R.id.member_iv)
        ImageView memberIv;
        @Bind(R.id.member_name_tv)
        TextView memberNameTv;
        @Bind(R.id.member_belong_tv)
        TextView memberBelongTv;
        @Bind(R.id.member_department_tv)
        TextView memberDepartmentTv;
        @Bind(R.id.member_reviews_info_tv)
        TextView memberReviewsInfoTv;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
