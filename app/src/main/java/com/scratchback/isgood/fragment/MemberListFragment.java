package com.scratchback.isgood.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.Maps;
import com.scratchback.isgood.R;
import com.scratchback.isgood.adapter.MemberAdpater;
import com.scratchback.isgood.listener.EndlessRecyclerViewScrollListener;
import com.scratchback.isgood.network.ApiConstant;
import com.scratchback.isgood.network.ApiRequester;
import com.scratchback.isgood.network.ApiService;
import com.scratchback.isgood.vo.Member;
import com.scratchback.isgood.vo.Members;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class MemberListFragment extends Fragment {

    private View view;

    @Bind(R.id.member_recycler_view)
    RecyclerView recyclerView;

    private Map<String, String> queryMap;

    private MemberAdpater memberAdpater;

    private List<Member> memberList;

    private boolean isRequesting = false;

    public MemberListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_member_list, container, false);
        }
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        initRecyclerView();

        Bundle args = getArguments();
        if (args != null) {
            String commonCodeType = args.getString(MemberFragment.ARGUMENT_COMMON_CODE_TYPE);
            String commonCodeId = args.getString(MemberFragment.ARGUMENT_COMMON_CODE_ID);
            queryMap = Maps.newHashMap();
            queryMap.put(commonCodeType, commonCodeId);
            requestMemberList(ApiConstant.REQUEST_FIRST_PAGE);
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (!isRequesting) {
                    requestMemberList(page);
                }
                return true;
            }
        });
    }

    private void requestMemberList(int page) {
        isRequesting = true;
        ApiService service = ApiRequester.getInstacne().getService();
        service.getMembers(page, queryMap).enqueue(new Callback<Members>() {
            @Override
            public void onResponse(Response<Members> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Members members = response.body();
                    if (memberList == null) {
                        memberList = members.getRows();
                        memberAdpater = new MemberAdpater(getActivity(), memberList);
                        recyclerView.setAdapter(memberAdpater);
                    } else {
                        if (members.getPage() <= members.getTotal()) {
                            memberList.addAll(members.getRows());
                            memberAdpater.notifyDataSetChanged();
                        }
                    }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
