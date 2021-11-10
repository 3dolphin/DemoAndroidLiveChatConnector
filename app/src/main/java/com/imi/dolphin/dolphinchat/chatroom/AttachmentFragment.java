package com.imi.dolphin.dolphinchat.chatroom;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;
import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinlib.connector.DolphinConnect;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttachmentFragment extends Fragment implements PickiTCallbacks {

    private ImageButton btnImage;
    private ImageButton btnVideo;
    private ImageButton btnAudio;
    private ImageButton btnDocument;
    private PickiT pickiT;
    private static final int SELECT_REQUEST = 777;
    private static final int PERMISSION_REQ_ID_RECORD_AUDIO = 22;
    private static final int PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE = PERMISSION_REQ_ID_RECORD_AUDIO + 1;

    private static final String MIME_TYPE_AUDIO = "audio/*";
    private static final String MIME_TYPE_TEXT = "application/*";
    private static final String MIME_TYPE_IMAGE = "image/*";
    private static final String MIME_TYPE_VIDEO = "video/*";

    private String state = "";

    public AttachmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attachment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        btnImage.setOnClickListener(v -> {
            state = MIME_TYPE_IMAGE;
            openGallery();
        });
        btnVideo.setOnClickListener(v -> {
            state = MIME_TYPE_VIDEO;
            openGallery();
        });
        btnAudio.setOnClickListener(v -> {
            state = MIME_TYPE_AUDIO;
            openGallery();
        });
        btnDocument.setOnClickListener(v -> {
            state = MIME_TYPE_TEXT;
            openGallery();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                pickiT.getPath(data.getData(), Build.VERSION.SDK_INT);
            }
        }
    }

    private void init(View view) {
        btnImage = view.findViewById(R.id.btn_atc_image);
        btnVideo = view.findViewById(R.id.btn_atc_video);
        btnAudio = view.findViewById(R.id.btn_atc_audio);
        btnDocument = view.findViewById(R.id.btn_atc_document);

        pickiT = new PickiT(getContext(), this, getActivity());
    }

    private boolean checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQ_ID_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  Permissions was granted, open the gallery
                openGallery();
            }
            //  Permissions was not granted
            else {
                Toast.makeText(getContext(), "No permission for " + Manifest.permission.WRITE_EXTERNAL_STORAGE, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery() {
        //  first check if permissions was granted
        if (checkSelfPermission()) {
            Intent intent;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            } else {
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI);
            }
            //  In this example we will set the type to video
            intent.setType(state);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra("return-data", true);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, SELECT_REQUEST);
        }
    }

    @Override
    public void PickiTonUriReturned() {

    }

    @Override
    public void PickiTonStartListener() {

    }

    @Override
    public void PickiTonProgressUpdate(int progress) {

    }

    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {
        if (wasSuccessful) {
            if (state.equals(MIME_TYPE_IMAGE)) {
                File file = new File(path);
                DolphinConnect.getInstance().sendFile(file, DolphinChat.Type.IMAGE);
            } else if (state.equals(MIME_TYPE_VIDEO)) {
                File file = new File(path);
                DolphinConnect.getInstance().sendFile(file, DolphinChat.Type.VIDEO);
            } else if (state.equals(MIME_TYPE_AUDIO)) {
                File file = new File(path);
                DolphinConnect.getInstance().sendFile(file, DolphinChat.Type.AUDIO);
            } else if (state.equals(MIME_TYPE_TEXT)) {
                File file = new File(path);
                DolphinConnect.getInstance().sendFile(file, DolphinChat.Type.DOCUMENT);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pickiT.deleteTemporaryFile();
    }
}
