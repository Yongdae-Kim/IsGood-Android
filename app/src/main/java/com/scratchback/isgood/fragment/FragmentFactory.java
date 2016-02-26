package com.scratchback.isgood.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by useruser on 2016. 2. 11..
 */
public class FragmentFactory {
    private static FragmentFactory factory;

    private FragmentFactory() {
    }

    public static FragmentFactory getInstacne() {
        if (factory == null) {
            factory = new FragmentFactory();
        }
        return factory;
    }


    public Fragment getFragment(FragmentTag tag) {
        switch (tag) {
            case MEMBERT:
                return new MemberFragment();
            case MEMBERT_LIST:
                return new MemberListFragment();
            case MEMBER_PROFILE:
                return new MemberProfileFragment();
            case MEMBER_WORK:
                return new MemberWorkFragment();
            case MEMBER_REVIEW:
                return new MemberReviewFragment();
            case MEMBER_STAT:
                return new MemberStatFragment();
            default:
                return null;
        }
    }

    public enum FragmentTag {
        MEMBERT, MEMBERT_LIST, MEMBER_PROFILE, MEMBER_WORK, MEMBER_REVIEW, MEMBER_STAT
    }
}
