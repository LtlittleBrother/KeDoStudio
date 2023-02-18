package com.nineton.ninetonlive2dsdk.bridge.utils;

import android.Manifest;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.util.Log;
import com.kedo.commonlibrary.application.BaseApplication;

import java.io.IOException;

import androidx.core.content.PermissionChecker;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class VisualizerPlayer {
    private Visualizer mVisualizer;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private float musicVolume = 1f;

    private VisualizerPlayer() {
        if (null == mediaPlayer) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setVolume(musicVolume, musicVolume);
                    mp.start();
                }
            });
        }
        try {
            audioManager = (AudioManager) BaseApplication.mApplication.getSystemService(Context.AUDIO_SERVICE);
            max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            cur = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            scale = cur / max;
        } catch (Exception e) {
            mediaPlayer.start();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (!isStop) {
//                    JniBridgeJava.nativeSetMouthOpenParameterValue(0f, 1);
                }

                releaseVisualizer();
            }
        });


    }

    public void setMusicVolume(float volume) {
        this.musicVolume = volume;
        mediaPlayer.setVolume(volume, volume);
    }

    private void releaseVisualizer() {
        if (mVisualizer != null) {
            mVisualizer.setEnabled(false);
        }
    }

    private void updateVisualizer() {
        try {
            if (mVisualizer != null) {
                mVisualizer.setEnabled(false);
                mVisualizer.release();
            }
            mVisualizer = new Visualizer(mediaPlayer.getAudioSessionId());
            mVisualizer.setEnabled(false);
            mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
            Visualizer.OnDataCaptureListener captureListener = new Visualizer.OnDataCaptureListener() {
                @Override
                public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
                                                  int samplingRate) {
                }

                @Override
                public void onFftDataCapture(Visualizer visualizer, byte[] bytes,
                                             int samplingRate) {
                    updateVisualizerFFT(bytes);
                    cur = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    scale = cur / max;
                }
            };
            mVisualizer.setDataCaptureListener(captureListener,
                    Visualizer.getMaxCaptureRate() / 2, false, true);
            mVisualizer.setEnabled(true);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private static VisualizerPlayer instance;

    public static VisualizerPlayer getInstance() {
        if (instance == null) {
            instance = new VisualizerPlayer();
        }
        return instance;
    }

    public void playVoice(String mp3Path) {
        isStop = false;
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(mp3Path);
            mediaPlayer.setLooping(false);
            mediaPlayer.prepareAsync();
            if (isHaveRecord()) {
                updateVisualizer();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    public void playVoiceAndSpeak(String mp3Path) {
        isStop = false;
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(mp3Path);
            mediaPlayer.setLooping(false);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
//                    Live2dManager.Companion.getInstance().speakNow(mp.getDuration());
                    if (!mp.isPlaying()){
                        mp.start();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void playVoiceAndSpeakAsset(int resId) {
        isStop = false;
        try {
            AssetFileDescriptor afd= BaseApplication.mApplication.getResources().openRawResourceFd(resId);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setLooping(false);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
//                    Live2dManager.Companion.getInstance().speakNow(mp.getDuration());
                    if (!mp.isPlaying()){
                        mp.start();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public boolean isHaveRecord() {
        int result = PermissionChecker.checkSelfPermission(BaseApplication.mApplication
                        .getApplicationContext()
                , Manifest.permission.RECORD_AUDIO);
        return result == PERMISSION_GRANTED && cur != 0f;
    }

    private boolean isStop = false;

    public void stop() {
        isStop = true;
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying())
                mediaPlayer.stop();
            if (mVisualizer != null) {
                mVisualizer.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public long getDuration() {
        return mediaPlayer.getDuration();
    }

    private void updateVisualizer(byte[] bytes) {
        computedbAmp(bytes);
    }

    private void updateVisualizerFFT(byte[] bytes) {
        computedbAmp(bytes);
    }

    /**
     * 预处理数据
     *
     * @return
     */
    private static byte[] readyFftDataByte(byte[] data) {
        byte[] newData = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            newData[i] = (byte) (Math.abs(data[i]));
        }
        return newData;
    }

    /**
     * 预处理数据
     *
     * @return
     */
    public static int[] getVoiceSizes(byte[] data) {
        byte[] newData = readyFftDataByte(data);
        int[] voices = new int[newData.length / 8];
        for (int i = 0; i < voices.length; i++) {
            int maxValue = 0;
            for (int j = i * 8; j < i * 8 + 8; j++) {
                if (newData[j] > maxValue) {
                    maxValue = newData[j];
                }
            }
            voices[i] = maxValue;
        }
        return voices;
    }

    private double computedbAmp(byte[] audioData) {
        byte[] newData = readyFftDataByte(audioData);
        int[] voices = getVoiceSizes(newData);

        int sum = 0;
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                sum += voices[i];
            } else if (i == 1) {
                sum += voices[i];
            } else if (i == 2) {
                sum += voices[i];
            }
        }
        float db = sum / 3;
        if (db > 0) {
            if (scale == 0) scale = 0.01f;
//            if (scale < 0.3) scale /= 1.2;
            if (musicVolume < 0.7) musicVolume = 0.7f;

            float mouthY = db / 120f / scale / musicVolume;

            Log.d("VisualizerPlayer", "db is " + db + "  ,mouthY is" + mouthY
                    + "  ,scale is" + scale + "  ,musicVolume is" + musicVolume);

            if (!isStop) {
//                JniBridgeJava.nativeSetMouthOpenParameterValue(mouthY, 1);
            }
        }
        return db;
    }

    private static float scale = 1;
    private static float max = 1;
    private static float cur = 1;

}
