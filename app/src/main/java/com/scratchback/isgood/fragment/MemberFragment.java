package com.scratchback.isgood.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scratchback.isgood.R;
import com.scratchback.isgood.activity.MainActivity;
import com.scratchback.isgood.vo.CommonCode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class MemberFragment extends Fragment {

    static final String ARGUMENT_COMMON_CODE_TYPE = MainActivity.ARGUMENT_COMMON_CODE_TYPE;
    static final String ARGUMENT_COMMON_CODE_ID = "ARGUMENT_COMMON_CODE_ID";


    private View view;

    @Bind(R.id.tab_host)
    MaterialTabHost tabHost;

    @Bind(R.id.view_pager)
    ViewPager pager;

    private String commonCodeType;
    private List<CommonCode> commonCodeList;

    public MemberFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_member, container, false);
        }
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        Bundle args = getArguments();
        if (args != null) {
            commonCodeType = args.getString(MainActivity.ARGUMENT_COMMON_CODE_TYPE);
            commonCodeList = (List<CommonCode>) args.getSerializable(MainActivity.ARGUMENT_COMMON_CODE_LIST);
        }
        initViewPager();
        initTabLayout();
    }

    private void initTabLayout() {
        for (int i = 0; i < commonCodeList.size(); i++) {
            tabHost.addTab(
                    tabHost.newTab().setText(commonCodeList.get(i).getName())
                            .setTabListener(materialTabListener)
            );
        }
    }

    private void initViewPager() {
        pager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
        pager.addOnPageChangeListener(pageChangeListener);
    }


    private ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            tabHost.setSelectedNavigationItem(position);
        }
    };

    private MaterialTabListener materialTabListener = new MaterialTabListener() {
        @Override
        public void onTabSelected(MaterialTab tab) {
            pager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabReselected(MaterialTab tab) {
        }

        @Override
        public void onTabUnselected(MaterialTab tab) {

        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        public Fragment getItem(int num) {
            Bundle args = new Bundle();
            args.clear();
            args.putString(ARGUMENT_COMMON_CODE_TYPE, commonCodeType);
            args.putString(ARGUMENT_COMMON_CODE_ID, commonCodeList.get(num).getId());
            Fragment memberListFragment = FragmentFactory.getInstacne().getFragment(FragmentFactory.FragmentTag.MEMBERT_LIST);
            memberListFragment.setArguments(args);
            return memberListFragment;
        }

        @Override
        public int getCount() {
            return commonCodeList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return commonCodeList.get(position).getName();

        }
    }
}
