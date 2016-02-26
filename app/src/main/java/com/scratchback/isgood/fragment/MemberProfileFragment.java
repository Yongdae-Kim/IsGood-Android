package com.scratchback.isgood.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.scratchback.isgood.R;
import com.scratchback.isgood.activity.MemberDetailActivity;
import com.scratchback.isgood.vo.Member;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class MemberProfileFragment extends Fragment {

    private View view;

    @Bind(R.id.member_address_tv)
    TextView memberAddressTv;
    @Bind(R.id.member_email_tv)
    TextView memberEmailTv;
    @Bind(R.id.member_homepage_tv)
    TextView memberHomepageTv;
    @Bind(R.id.member_birth_tv)
    TextView memberBirthTv;
    @Bind(R.id.member_career_tv)
    TextView memberCareerTv;
    @Bind(R.id.member_military_tv)
    TextView memberMilitaryTv;
    @Bind(R.id.member_crime_tv)
    TextView memberCrimeTv;

    public MemberProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_member_profile, container, false);
        }
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        Bundle args = getArguments();
        if (args != null) {
            Member member = (Member) args.getSerializable(MemberDetailActivity.ARGUMENT_MEMBER);
            initMemberProfile(member);
        }
    }

    private void initMemberProfile(Member member) {
        memberAddressTv.setText(member.getAddress());
        memberEmailTv.setText(member.getEmail());
        memberHomepageTv.setText(member.getHomepage());
        memberBirthTv.setText(member.getBirth());
        memberCareerTv.setText(Html.fromHtml(member.getCareer()));
        memberMilitaryTv.setText(Html.fromHtml(member.getMilitary()));
        memberCrimeTv.setText(Html.fromHtml(member.getCrime()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
