package com.scratchback.isgood.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.scratchback.isgood.R;
import com.scratchback.isgood.adapter.ChatAdpater;
import com.scratchback.isgood.application.ChatApplication;
import com.scratchback.isgood.preferences.AvatarPreferencesManager;
import com.scratchback.isgood.util.ToastSystem;
import com.scratchback.isgood.vo.Chat;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatingRoomActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.chat_recycle_view)
    RecyclerView chatRecycleView;
    @Bind(R.id.chat_input_tv)
    EditText chatInputTv;

    private Socket socket;

    private Gson gson;

    private ChatAdpater chatAdapter;
    private List<Chat> chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chating_room);
        ButterKnife.bind(this);

        init();

        gson = new Gson();

        ChatApplication app = (ChatApplication) getApplication();
        socket = app.getSocket();
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        socket.on("new message", onNewMessage);
        socket.on("user joined", onUserJoined);
        socket.on("user left", onUserLeft);

        socket.connect();

        chats = Lists.newArrayList();
        chatAdapter = new ChatAdpater(this, chats);
        chatRecycleView.setLayoutManager(new LinearLayoutManager(this));
        chatRecycleView.setAdapter(chatAdapter);
    }

    private void init() {
        initToolBar();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("정치 토론방");
    }

    private void addMessage(Chat chat) {
        chats.add(chat);
        chatAdapter.notifyItemInserted(chats.size() - 1);
        chatRecycleView.scrollToPosition(chatAdapter.getItemCount() - 1);
    }

    @OnClick(R.id.send_btn)
    public void sendMessage() {
        if (!socket.connected()) {
            return;
        }

        String message = chatInputTv.getText().toString();
        if (chatInputTv.getText().toString().trim().length() == 0) {
            chatInputTv.requestFocus();
            ToastSystem.getInstacne().show(this, ToastSystem.NO_CONTENT);
            return;
        }

        chatInputTv.setText("");

        Chat chat = new Chat();
        chat.setNickname(AvatarPreferencesManager.getInstacne(this).get().getNickname());
        chat.setMessage(message);
        chat.setIsMine(true);

        addMessage(chat);

        socket.emit("add user", chat.getNickname());
        socket.emit("new message", chat.getMessage());
    }

    private Emitter.Listener onConnectError = args -> runOnUiThread(() -> Toast.makeText(getApplicationContext(),
            "Error", Toast.LENGTH_SHORT).show());

    private Emitter.Listener onNewMessage = args -> runOnUiThread(() -> {
        Chat chat = mappingToChatFromJsonObj(args[0]);
        addMessage(chat);

    });

    private Emitter.Listener onUserJoined = args -> runOnUiThread(() -> {
        Chat chat = mappingToChatFromJsonObj(args[0]);
    });

    private Emitter.Listener onUserLeft = args -> runOnUiThread(() -> {
        Chat chat = mappingToChatFromJsonObj(args[0]);
    });

    private Chat mappingToChatFromJsonObj(Object obj) {
        JSONObject data = (JSONObject) obj;
        Chat chat = gson.fromJson(data.toString(), Chat.class);
        String myNickname = AvatarPreferencesManager.getInstacne(this).get().getNickname();
        boolean isMine = false;
        if (chat.getNickname().equals(myNickname)) {
            isMine = true;
        }
        chat.setIsMine(isMine);
        return chat;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
        socket.off("new message", onNewMessage);
    }
}

