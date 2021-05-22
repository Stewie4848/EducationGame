package com.example.educationgame;

import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AudioManager implements SoundPool.OnLoadCompleteListener {
    private Map<Sound, Integer> soundList;
    private final SoundPool pool;
    private boolean ready;
    private int loadId;


    AudioManager(Context context) {
        soundList = new HashMap<>();
        pool = new SoundPool(10, android.media.AudioManager.STREAM_MUSIC, 0);
        pool.setOnLoadCompleteListener(this);

        pool.load(context, R.raw.correct, 0);
        pool.load(context, R.raw.incorrect, 0);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

        this.ready = status == 0;
        Sound sound = Sound.values()[loadId++];
        Log.i("AudioManager", "loaded sound: " + sound);
        soundList.put(sound, sampleId);
    }

    boolean isReady() { return ready;}

    void play(Sound sound) {
        Integer sampleId = soundList.get(sound);
        assert sampleId != null;
        pool.play(sampleId, 1, 1, 1, 0, 1);

    }



}
