package com.Rengar.Plugins.ogg;

import com.Rengar.Plugins.IPlayerPlugin;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class OGGPlayerPlugin implements IPlayerPlugin{

    class PlayThread implements Runnable{
        public void run(){
            try{
                AudioFormat outFormat = getOutFormat(in.getFormat());
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, outFormat);
                line = (SourceDataLine) AudioSystem.getLine(info);
                if (line != null) {
                    line.open(outFormat);
                    line.start();
                    stream(AudioSystem.getAudioInputStream(outFormat, in), line);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class StopThread implements Runnable{
        public void run(){
            try {
                line.stop();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private String filePath;
    private AudioInputStream in;
    private SourceDataLine line;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void service(){
        File file = new File(filePath);
        try{
            in = AudioSystem.getAudioInputStream(file);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void play(){
        new Thread(new PlayThread()).start();
    }

    public void stopAudio(){
       new Thread(new StopThread()).start();
    }

    private AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();
        final float rate = inFormat.getSampleRate();
        return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }

    private void stream(AudioInputStream in, SourceDataLine line)
            throws IOException {
        final byte[] buffer = new byte[65536];
        for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
            line.write(buffer, 0, n);
        }
    }
}
