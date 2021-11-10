package com.imi.dolphin.dolphinchat.adapter.viewholder;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.downloader.Status;
import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;

public class ChatReceivedDocumentViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvTitle;
    private final TextView tvDocumentTime;
    private final ImageButton btnDownload;
    private String filename;

    public ChatReceivedDocumentViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_document_title);
        tvDocumentTime = itemView.findViewById(R.id.tv_received_document);
        btnDownload = itemView.findViewById(R.id.btn_download_document);
    }

    public void bindItem(DolphinChat dolphinChat) {
        String[] link = dolphinChat.getMediaLink().split("/");
        String[] nameWithAccessToken = link[link.length - 1].split("\\?");
        filename = nameWithAccessToken[0];

        tvTitle.setText(filename);
        tvDocumentTime.setText(String.valueOf(dolphinChat.getCreatedAt()));

        btnDownload.setOnClickListener(v -> downloadFile(dolphinChat.getMediaLink()));
    }

    private void downloadFile(String url) {
        PRDownloader.initialize(itemView.getContext());
        String dirPath = Environment.getExternalStorageDirectory().toString();

        int downloadId = PRDownloader.download(url, dirPath, filename)
                .build()
                .setOnStartOrResumeListener(() -> {

                })
                .setOnPauseListener(() -> {

                })
                .setOnCancelListener(() -> {

                })
                .setOnProgressListener(progress -> {

                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        tvTitle.setText(String.format("File saved to %s%s", dirPath, filename));
                        Toast.makeText(itemView.getContext(), "File saved to " + dirPath + filename, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Error error) {
                        Log.e("DownloadFile", error.getServerErrorMessage());
                    }

                });

        Status status = PRDownloader.getStatus(downloadId);
        Log.e("DOWNLOAD", status.name());
    }
}
