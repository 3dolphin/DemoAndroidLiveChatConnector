package com.imi.dolphin.dolphinchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.imi.dolphin.dolphinchat.chatroom.ChatActivity;
import com.imi.dolphin.dolphinlib.data.model.DolphinProfile;

import static android.provider.Settings.*;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText edtUsername;
    private EditText edtEmail;
    private EditText edtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btnLogin.setOnClickListener(v -> {
            if (checkValidity()){
                DolphinProfile dolphinProfile = new DolphinProfile();
                dolphinProfile.setUid("12345");
                dolphinProfile.setCustomerId("");
                dolphinProfile.setUsername(edtUsername.getText().toString());
                dolphinProfile.setEmail(edtEmail.getText().toString());
                dolphinProfile.setPhoneNumber(edtPhone.getText().toString());

                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra("profile", dolphinProfile);
                startActivity(intent);
            }
        });
    }

    private void init(){
        btnLogin = findViewById(R.id.btn_login);
        edtUsername = findViewById(R.id.edt_username);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);
    }

    private boolean checkValidity(){
        if (TextUtils.isEmpty(edtUsername.getText())){
            edtUsername.setError("Name Can't Empty");
            return false;
        } else if (TextUtils.isEmpty(edtEmail.getText())){
            edtEmail.setError("Email Can't Empty");
            return false;
        } else if (TextUtils.isEmpty(edtPhone.getText())){
            edtPhone.setError("Phone Number Can't Empty");
            return false;
        } else {
            return true;
        }
    }
}
