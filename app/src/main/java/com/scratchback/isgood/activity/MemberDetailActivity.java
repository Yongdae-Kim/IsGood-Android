package com.scratchback.isgood.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.orm.SugarApp;
import com.orm.SugarDb;
import com.orm.SugarRecord;
import com.orm.util.SugarCursorFactory;
import com.scratchback.isgood.R;
import com.scratchback.isgood.fragment.FragmentFactory;
import com.scratchback.isgood.network.ApiRequester;
import com.scratchback.isgood.network.ApiService;
import com.scratchback.isgood.preferences.AvatarPreferencesManager;
import com.scratchback.isgood.util.ToastSystem;
import com.scratchback.isgood.vo.Member;
import com.scratchback.isgood.vo.MyMember;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static android.support.design.widget.TabLayout.GRAVITY_FILL;
import static android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;

public class MemberDetailActivity extends AppCompatActivity {

    public static final String ARGUMENT_MEMBER = "ARGUMENT_MEMBER";
    public static final String ARGUMENT_MEMBER_ID = "ARGUMENT_MEMBER_ID";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout layout;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.pager)
    ViewPager pager;

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
    @Bind(R.id.add_favorite_tv)
    TextView addFavoriteTv;
    @Bind(R.id.remove_favorite_tv)
    TextView removeFavoriteTv;

    private Member member;
    private MyMember myMember;
    private List<MyMember> myMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id = extras.getInt(MainActivity.EXTRA_MEMBER_ID);
            requestMember(id);
        }
        initToolBar();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        layout.setTitle("국회의원 상세보기");
    }

    private void requestMember(int id) {
        ApiService service = ApiRequester.getInstacne().getService();
        service.getMember(id).enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Response<Member> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    member = response.body();
                    myMember = new MyMember(member);

                    initMemberView(member);
                    initTabLayout();
                    initViewPager();
//                    initFavorite();
                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void initFavorite() {
        myMembers = Lists.newArrayList();
        initRemoveFavorite();
        initAddFavorite();
        if (myMembers.size() != 0) {
            if (myMembers.contains(myMember)) {
                showRemoveFavorite();
            } else {
                showAddFavorite();
            }
        } else {
            showAddFavorite();
        }
    }

    private void initAddFavorite() {
        addFavoriteTv.bringToFront();
        addFavoriteTv.setOnClickListener(v -> {
            if (myMembers.size() == 0) {
                System.out.println(myMember);
                myMember.save();
            } else {
                if (!myMembers.contains(myMember)) {
                    myMember.save();
                }
            }
            showRemoveFavorite();
            myMembers = MyMember.listAll(MyMember.class);
            printMyMembers();
        });
    }

    private void initRemoveFavorite() {
        removeFavoriteTv.bringToFront();
        removeFavoriteTv.setOnClickListener(v -> {
            if (myMembers.size() != 0) {
                if (myMembers.contains(myMember)) {
                    MyMember selectedMyMember = MyMember.find(MyMember.class, "name = ?", myMember.getName()).get(0);
                    selectedMyMember.delete();
                }
            }
            showAddFavorite();
            myMembers = MyMember.listAll(MyMember.class);
            printMyMembers();
        });
    }

    private void printMyMembers() {
        System.out.println("##############################");
        for (MyMember m : myMembers) {
            System.out.println(m);
        }
        System.out.println("##############################");
    }

    private void showAddFavorite() {
        removeFavoriteTv.setVisibility(View.INVISIBLE);
        addFavoriteTv.setVisibility(View.VISIBLE);
    }

    private void showRemoveFavorite() {
        addFavoriteTv.setVisibility(View.INVISIBLE);
        removeFavoriteTv.setVisibility(View.VISIBLE);
    }

    private void initMemberView(Member member) {
        Picasso.with(this)
                .load(member.getImage())
                .into(memberIv);
        memberNameTv.setText(member.getName());
        memberBelongTv.setText(member.getBelong());
        memberDepartmentTv.setText(member.getDepartment());
        memberReviewsInfoTv.setText(member.getReviewsInfo());
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("신상정보"));
        tabLayout.addTab(tabLayout.newTab().setText("의정활동"));
        tabLayout.addTab(tabLayout.newTab().setText("한줄평 보기"));
        tabLayout.addTab(tabLayout.newTab().setText("능력치 보기"));
        tabLayout.setTabGravity(GRAVITY_FILL);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViewPager() {
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return tabLayout.getTabCount();
            }

            @Override
            public Fragment getItem(int position) {
                Bundle args = new Bundle();
                args.clear();
                FragmentFactory.FragmentTag tag = null;
                switch (position) {
                    case 0:
                        args.putSerializable(ARGUMENT_MEMBER, member);
                        tag = FragmentFactory.FragmentTag.MEMBER_PROFILE;
                        break;
                    case 1:
                        args.putSerializable(ARGUMENT_MEMBER, member);
                        tag = FragmentFactory.FragmentTag.MEMBER_WORK;
                        break;
                    case 2:
                        args.putInt(ARGUMENT_MEMBER_ID, member.getId());
                        tag = FragmentFactory.FragmentTag.MEMBER_REVIEW;
                        break;
                    case 3:
                        args.putSerializable(ARGUMENT_MEMBER, member);
                        tag = FragmentFactory.FragmentTag.MEMBER_STAT;
                        break;
                }
                Fragment fragment = FragmentFactory.getInstacne().getFragment(tag);
                fragment.setArguments(args);
                return fragment;
            }
        });
        pager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(tabLayout));
    }


}
