package com.imi.dolphin.dolphinchat.utils;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class Utils {
    public static MediaSource buildMediaSource(Uri uri, Context context) {
        MediaSource mediaSource;

        if (uri.toString().startsWith("http://") || uri.toString().startsWith("https://")){
            DataSource.Factory dataSourceFactory =
                    new DefaultHttpDataSourceFactory("dolphins");
            mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        } else {
            DataSource.Factory dataSourceFactoryFile = new DefaultDataSourceFactory(
                    context, Util.getUserAgent(context, "SongShakes"));
            mediaSource = new ExtractorMediaSource.Factory(dataSourceFactoryFile).
                    createMediaSource(uri);
        }
        return mediaSource;
    }
}
