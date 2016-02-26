package com.scratchback.isgood.activity;

import android.animation.LayoutTransition;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.scratchback.isgood.R;
import com.scratchback.isgood.fragment.FragmentFactory;
import com.scratchback.isgood.network.ApiRequester;
import com.scratchback.isgood.network.ApiService;
import com.scratchback.isgood.preferences.AvatarPreferencesManager;
import com.scratchback.isgood.util.BackPressCloseSystem;
import com.scratchback.isgood.util.ToastSystem;
import com.scratchback.isgood.vo.Avatar;
import com.scratchback.isgood.vo.Member;
import com.scratchback.isgood.vo.MyMember;
import com.scratchback.isgood.vo.Party;
import com.scratchback.isgood.vo.Region;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    public static final String SUCCESS_COLOR = "#5cb85c";
    public static final String INFO_COLOR = "#5bc0de";
    public static final String PRIMARY_COLOR = "#428bca";
    public static final String WARNING_COLOR = "#f0ad4e";
    public static final String DANGER_COLOR = "#d9534f";

    public static final String EXTRA_MEMBER_ID = "EXTRA_MEMBER_ID";
    public static final String ARGUMENT_COMMON_CODE_TYPE = "ARGUMENT_COMMON_CODE_TYPE";
    public static final String ARGUMENT_COMMON_CODE_LIST = "ARGUMENT_COMMON_CODE_LIST";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    private BackPressCloseSystem backPressCloseSystem;
    private SearchView searchView;

    private List<Party> partyList;
    private List<Region> regionList;
    private List<Member> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TypefaceProvider.registerDefaultIconSets();
        ButterKnife.bind(this);

        initView();

        if (!AvatarPreferencesManager.getInstacne(this).isPreferencesExisted()) {
            reguestAvatar();
        }

        requestPartyList();
        requestRegionList();
        requestMembersAutocomplete();
//        MyMember.deleteAll(MyMember.class);
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(listener);
        backPressCloseSystem = new BackPressCloseSystem(this);
    }


    // 이 중복을 어떻게 해결할까 쉣 망할 드럽다
    public void reguestAvatar() {
        ApiService service = ApiRequester.getInstacne().getService();
        service.registerAvatar().enqueue(new Callback<Avatar>() {
            @Override
            public void onResponse(Response<Avatar> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    AvatarPreferencesManager.getInstacne(MainActivity.this).save(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }


    private void requestPartyList() {
        ApiService service = ApiRequester.getInstacne().getService();
        service.getPartyList().enqueue(new Callback<List<Party>>() {
            @Override
            public void onResponse(Response<List<Party>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    partyList = response.body();
                    setNavigationViewFirstItemClicked();
                } else {
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void setNavigationViewFirstItemClicked() {
        MenuItem initItem = navigationView.getMenu().getItem(0);
        navigationView.setCheckedItem(initItem.getItemId());
        listener.onNavigationItemSelected(initItem);
    }

    private void requestRegionList() {
        ApiService service = ApiRequester.getInstacne().getService();
        service.getRegionList().enqueue(new Callback<List<Region>>() {
            @Override
            public void onResponse(Response<List<Region>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    regionList = response.body();
                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void requestMembersAutocomplete() {
        ApiService service = ApiRequester.getInstacne().getService();
        service.getMembersAutocomplete().enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Response<List<Member>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    memberList = response.body();
                    initSearchAutoComplete(Lists.transform(memberList, member -> member.getName()));
                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void initSearchAutoComplete(List<String> autocompleteList) {
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setTextColor(Color.WHITE);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, autocompleteList);
        searchAutoComplete.setAdapter(adapter);
        searchAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            String searchString = parent.getItemAtPosition(position).toString();
            searchAutoComplete.setText(searchString);

            Member filtered = Iterables.find(memberList, input -> input.getName().equals(searchString));

            Intent intent = new Intent(this, MemberDetailActivity.class);
            intent.putExtra(EXTRA_MEMBER_ID, filtered.getId());
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        initSearchView(menu);
        return true;
    }

    private void initSearchView(Menu menu) {
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("국회의원 이름을 입력하세요.");
        SearchManager searchManager =
                (SearchManager) getSystemService(this.SEARCH_SERVICE);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        LinearLayout searchBar = (LinearLayout) searchView.findViewById(R.id.search_bar);
        searchBar.setLayoutTransition(new LayoutTransition());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return false;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private NavigationView.OnNavigationItemSelectedListener listener = item -> {
        Bundle args = new Bundle();
        args.clear();
        switch (item.getItemId()) {
            case R.id.nav_party:
                args.putString(ARGUMENT_COMMON_CODE_TYPE, "party");
                args.putSerializable(ARGUMENT_COMMON_CODE_LIST, (Serializable) partyList);
                replaceFragment(FragmentFactory.FragmentTag.MEMBERT, args);
                getSupportActionBar().setTitle("정당별 국회의원 목록");
                break;
            case R.id.nav_region:
                args.putString(ARGUMENT_COMMON_CODE_TYPE, "region");
                args.putSerializable(ARGUMENT_COMMON_CODE_LIST, (Serializable) regionList);
                replaceFragment(FragmentFactory.FragmentTag.MEMBERT, args);
                getSupportActionBar().setTitle("지역별 국회의원 목록");
                break;
            case R.id.nav_favorite:
//                startActivity(new Intent(this, FavoriteActivity.class));
                ToastSystem.getInstacne().show(this, ToastSystem.NOT_READY_FUNCTION);
                break;
            case R.id.nav_chating:
                startActivity(new Intent(this, ChatingRoomActivity.class));
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    };


    private void replaceFragment(FragmentFactory.FragmentTag tag, Bundle args) {
        Fragment newFragment = FragmentFactory.getInstacne().getFragment(tag);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragment_layout, newFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            backPressCloseSystem.onBackPressed();
        }
    }
}
