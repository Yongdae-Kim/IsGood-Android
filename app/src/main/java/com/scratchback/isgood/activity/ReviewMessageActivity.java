package com.scratchback.isgood.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.scratchback.isgood.R;
import com.scratchback.isgood.adapter.MessageAdpater;
import com.scratchback.isgood.fragment.MemberReviewFragment;
import com.scratchback.isgood.network.ApiRequester;
import com.scratchback.isgood.network.ApiService;
import com.scratchback.isgood.preferences.AvatarPreferencesManager;
import com.scratchback.isgood.util.ToastSystem;
import com.scratchback.isgood.vo.Message;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ReviewMessageActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.message_recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.register_message_content_tv)
    TextView register_message_content_tv;

    private int memberId;
    private int reviewId;

    private MessageAdpater messageAdpater;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_message);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            memberId = extras.getInt(MemberReviewFragment.EXTRA_MEMBER_ID);
            reviewId = extras.getInt(MemberReviewFragment.EXTRA_REVIEW_ID);
            requestMessages(memberId, reviewId);
            initRecyclerView();
        }
        initToolBar();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("리뷰에 의견 남기기");
    }


    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }


    private void requestMessages(int memberId, int reviewId) {
        ApiService service = ApiRequester.getInstacne().getService();
        service.getMessages(memberId, reviewId).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Response<List<Message>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    messageList = response.body();
                    messageAdpater = new MessageAdpater(ReviewMessageActivity.this, messageList);
                    recyclerView.setAdapter(messageAdpater);
                } else {
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    @OnClick(R.id.register_message_btn)
    public void registerMessage() {
        ApiService service = ApiRequester.getInstacne().getService();
        int avatarId = AvatarPreferencesManager.getInstacne(ReviewMessageActivity.this).get().getId();
        String content = register_message_content_tv.getText().toString();

        if (register_message_content_tv.getText().toString().trim().length() == 0) {
            register_message_content_tv.requestFocus();
            ToastSystem.getInstacne().show(this, ToastSystem.NO_CONTENT);
            return;
        }

        service.registerMessage(memberId, reviewId, reviewId, avatarId, content)
                .enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Response<Message> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            initRegisterForm();
                            requestMessages(memberId, reviewId);
                            recyclerView.scrollToPosition(messageAdpater.getItemCount() - 1);
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                    }
                });
    }

    private void initRegisterForm() {
        register_message_content_tv.setText("");

    }
}
