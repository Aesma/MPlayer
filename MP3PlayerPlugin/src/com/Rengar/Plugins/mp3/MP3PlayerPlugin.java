package com.Rengar.Plugins.mp3;

import java.io.File;

import java.io.BufferedInputStream;
import java.io.FileInputStream;


import com.Rengar.Plugins.IPlayerPlugin;
import javazoom.jl.player.*;

public class MP3PlayerPlugin implements IPlayerPlugin {
    class PlayThread implements Runnable{
        @Override
        public void run(){
            try {
                player.play();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    class StopThread implements Runnable{
        @Override
        public void run(){
            try {
                if(player != null) {
                    player.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    Player player;
    private String filePath;

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void service(){
        try {
            File file = new File(filePath);
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(file));
            player = new Player(buffer);
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
    }}
