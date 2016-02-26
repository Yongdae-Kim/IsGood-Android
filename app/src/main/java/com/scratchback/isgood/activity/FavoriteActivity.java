package com.scratchback.isgood.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.scratchback.isgood.R;
import com.scratchback.isgood.adapter.FavoriteAdpater;
import com.scratchback.isgood.adapter.ReviewAdpater;
import com.scratchback.isgood.listener.EndlessRecyclerViewScrollListener;
import com.scratchback.isgood.vo.MyMember;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.favorite_recycler_view)
    RecyclerView recyclerView;

    private FavoriteAdpater favoriteAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        }
        initToolBar();
        initRecyclerView();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("관심있는 국회의원 목록");
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        favoriteAdpater = new FavoriteAdpater(this, MyMember.listAll(MyMember.class));
        recyclerView.setAdapter(favoriteAdpater);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                return false;
            }
        });
    }
}
