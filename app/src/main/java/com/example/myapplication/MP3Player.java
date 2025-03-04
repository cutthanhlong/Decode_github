package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import androidx.core.content.FileProvider;
import java.io.File;

/* loaded from: classes5.dex */
public class MP3Player {
    private Context context;
    private int length;
    private MediaPlayer mp;

    public interface CompleteListener {
        void responseToComplete();
    }

    public MP3Player(Context context, final CompleteListener completeListener) {
        this.context = context;
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mp = mediaPlayer;
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: notes.easy.android.mynotes.view.record.MP3Player.1
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer2) {
                completeListener.responseToComplete();
            }
        });
    }

    public void destroy() {
        this.mp.release();
    }

    public long getCurrentPosition() {
        return this.mp.getCurrentPosition();
    }

    public int getLength() {
        return this.length;
    }

    public int getPosition() {
        return this.mp.getCurrentPosition();
    }

    public void init(Uri uri, int i6, String str) {
        try {
            this.mp.reset();
            this.mp.setDataSource(this.context, uri);
            this.mp.prepare();
            this.length = this.mp.getDuration();
        } catch (Exception e7) {
            if (i6 == 1 && !TextUtils.isEmpty(str)) {
                Intent intent = new Intent(str);
                intent.setAction("android.intent.action.VIEW");
                File file = new File(str);
                intent.setDataAndType(FileProvider.getUriForFile(this.context, this.context.getPackageName() + ".fileprovider", file), "audio/*");
                try {
                    intent.addFlags(3);
                    this.context.startActivity(intent);
                } catch (Exception unused) {
                }
            }
            e7.printStackTrace();
        }
    }

    public boolean isPalying() {
        return this.mp.isPlaying();
    }

    public void pause() {
        this.mp.pause();
    }

    public void play() {
        this.mp.start();
    }

    public void seekTo(int i6) {
        this.mp.seekTo(i6);
    }

    public void setLoopPlay() {
        this.mp.setLooping(true);
    }

    public void setVolume(float f6, float f7) {
        this.mp.setVolume(f6, f7);
    }

    public void stop() {
        this.mp.stop();
    }
}
