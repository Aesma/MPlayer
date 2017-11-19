package com.Rengar.Plugins.wav;

import com.Rengar.Plugins.IPlayerPlugin;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.InputStream;

public class WAVPlayerPlugin implements IPlayerPlugin{
    private AudioStream as;
    private String filePath;

    class PlayThread implements Runnable{
        @Override
        public void run(){
            try{
                AudioPlayer.player.start(as);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class StopThread implements Runnable{
        @Override
        public void run(){
            AudioPlayer.player.stop(as);
        }
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void service(){
        try{
            InputStream in = new FileInputStream(filePath);
            as = new AudioStream(in);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void play(){
        new Thread(new PlayThread()).start();
    }

    @Override
    public void stopAudio(){
        new Thread(new StopThread()).start();
    }
}
