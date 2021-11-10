package com.imi.dolphin.dolphinchat.chatroom;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinchat.adapter.ChatListAdapter;
import com.imi.dolphin.dolphinchat.adapter.OnItemClickListener;
import com.imi.dolphin.dolphinlib.connector.DolphinConnect;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;
import com.imi.dolphin.dolphinlib.data.model.DolphinProfile;
import com.imi.dolphin.dolphinlib.event.DolphinChatEvent;
import com.imi.dolphin.dolphinlib.event.DolphinChatHistoryEvent;
import com.imi.dolphin.dolphinlib.event.DolphinConnectEvent;
import com.imi.dolphin.dolphinlib.event.DolphinQueueEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ChatActivity extends AppCompatActivity implements OnItemClickListener {

    private ChatListAdapter adapter;
    private DolphinProfile dolphinProfile;
    private RecyclerView rvChatList;
    private TextView edtInputMessage;
    private ImageButton btnSend, btnAttachment;
    private boolean attachmentShow = false;
    private FrameLayout frameAttachment;

    private final List<DolphinChat> dolphinChatList = new ArrayList<>();

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Connecting");
        progressDialog.show();
        dolphinProfile = getIntent().getParcelableExtra("profile");

        try {
            JSONArray customVariables = new JSONArray();
            JSONObject field = new JSONObject();
            field.put("name", "Doni");
            customVariables.put(field);

            dolphinProfile.setCustomVariables(customVariables);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Welcome to Dolphin Chat");
            getSupportActionBar().setSubtitle("Our agent will serve you shortly");
        }

        adapter = new ChatListAdapter(this);

        init();

        try {
            DolphinConnect.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnSend.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtInputMessage.getText())) {
                edtInputMessage.setError("masukkan pesan");
            } else {
                sendMessage(edtInputMessage.getText().toString());
            }
        });

        btnAttachment.setOnClickListener(v -> {
            if (attachmentShow) {
                attachmentShow = false;
                frameAttachment.setVisibility(View.GONE);
            } else {
                attachmentShow = true;
                frameAttachment.setVisibility(View.VISIBLE);
            }
        });
    }

    private void init() {
        rvChatList = findViewById(R.id.rv_chat_room);
        edtInputMessage = findViewById(R.id.edt_message_input);
        btnSend = findViewById(R.id.btn_send_message);
        btnAttachment = findViewById(R.id.btn_attachment);
        frameAttachment = findViewById(R.id.frame_attachment);

        Fragment fragmentAttachment = new AttachmentFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_attachment, fragmentAttachment, fragmentAttachment.getClass().getSimpleName())
                .commit();

        adapter = new ChatListAdapter(this);
        rvChatList.setLayoutManager(new LinearLayoutManager(this));

        DolphinConnect.getInstance().init(dolphinProfile);
    }

    private void sendMessage(String text) {
        edtInputMessage.setText("");
        DolphinConnect.getInstance().sendMessage(text);
    }

    @Subscribe
    public void connectionCallback(DolphinConnectEvent event) {
        Timber.e(event.getStatus().toString());
    }

    @Subscribe
    public void chatCallback(DolphinChatHistoryEvent event) {
        if (event.getStatus().equals(DolphinChatHistoryEvent.Status.SUCCESS)) {
            dolphinChatList.addAll(event.getDolphinChats());
            adapter.setData(dolphinChatList);
            rvChatList.setAdapter(adapter);
            rvChatList.smoothScrollToPosition(adapter.getItemCount());
            progressDialog.dismiss();
            Timber.tag("chat").e("CHAT");
            Timber.tag("chat").e(event.getDolphinChats().toString());
        } else {
            Timber.tag("CHAT").e(event.getMessage());
        }
    }

    @Subscribe
    public void getQueue(DolphinQueueEvent event) {
        Timber.tag("QUEUE").e(String.valueOf(event.getQueue()));
    }

    @Subscribe
    public void receiveMessage(DolphinChatEvent event) {
        Timber.tag("receivechat").e("CHAT");
        switch (event.getStatus()) {
            case CHAT:
                if (event.getDolphinChat() != null) {
                    dolphinChatList.add(event.getDolphinChat());
                    adapter.setData(dolphinChatList);
                    rvChatList.smoothScrollToPosition(adapter.getItemCount());
                    Timber.tag("receivechat").e(event.getDolphinChat().toString());
                }
                break;
            case MESSAGE_READ:
                Timber.tag("EVENT").e(event.getStatus().toString());
                for (int i = dolphinChatList.size() - 1; i >= 0; i--) {
                    if (!dolphinChatList.get(i).isAnswer()) {
                        if (dolphinChatList.get(i).getId().equals(event.getChatId())) {
                            dolphinChatList.get(i).setState(DolphinChat.State.READ);
                            adapter.setData(dolphinChatList);
                            break;
                        }
                    }
                }
                break;
            case MESSAGE_SENT:
                Timber.tag("EVENT").e(event.getStatus().toString());
                for (int i = dolphinChatList.size() - 1; i >= 0; i--) {
                    if (!dolphinChatList.get(i).isAnswer()) {
                        if (dolphinChatList.get(i).getId().equals(event.getChatId())) {
                            dolphinChatList.get(i).setState(DolphinChat.State.SENT);
                            adapter.setData(dolphinChatList);
                            break;
                        }
                    }
                }
                break;
            case MESSAGE_SEND_FAILED:
                Timber.tag("EVENT").e(event.getStatus().toString());
                Toast.makeText(this, "Send Message Fail", Toast.LENGTH_SHORT).show();
                break;
            case AGENT_TYPING:
                Timber.tag("EVENT TYPING").e(event.getStatus().toString());
                break;
        }
    }

    @Override
    public void onItemClick(String value, DolphinChat.Type type) {
        Timber.tag("OnItemClick").e(value + " = " + type);
        sendMessage(value);
    }

    @Override
    public void onBackPressed() {
        DolphinConnect.getInstance().closeConnection();
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}