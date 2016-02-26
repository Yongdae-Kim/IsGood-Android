package com.scratchback.isgood.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scratchback.isgood.R;
import com.scratchback.isgood.activity.MemberDetailActivity;
import com.scratchback.isgood.util.ToastSystem;
import com.scratchback.isgood.vo.Member;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class MemberWorkFragment extends Fragment {

    private View view;

    @Bind(R.id.main_session_attendance_tv)
    TextView mainSessionTv;
    @Bind(R.id.sub_session_attendance_tv)
    TextView subSessionTv;
    @Bind(R.id.bill_proposal_btn)
    TextView billProposalBtn;

    public MemberWorkFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_member_work, container, false);
        }
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        Bundle args = getArguments();
        if (args != null) {
            Member member = (Member) args.getSerializable(MemberDetailActivity.ARGUMENT_MEMBER);
            initMemberWork(member);
        }
    }

    @OnClick({R.id.main_session_vote_btn, R.id.bill_proposal_btn})
    public void notWorkingBtn() {
        ToastSystem.getInstacne().show(getActivity(), ToastSystem.NOT_READY_FUNCTION);
    }

    private void initMemberWork(Member member) {
        mainSessionTv.setText(Html.fromHtml(member.getMainSessionAttendance()));
        subSessionTv.setText(Html.fromHtml(member.getSubSessionAttendance()));
        billProposalBtn.setText(member.getBillProposal());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
