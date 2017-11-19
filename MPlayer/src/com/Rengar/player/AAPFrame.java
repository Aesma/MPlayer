package com.Rengar.player;

import com.Rengar.player.pluginParser.Plugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class AAPFrame {
    private JPanel jpanel;
    private JButton puginButton;
    private JButton playButton;
    private JButton stopButton;
    private JButton getListButton;
    private JButton helpButton;
    private JButton aboutButton;
    private AAPlayer aaPlayer;

    public AAPFrame(){
        aaPlayer = new AAPlayer();
        JFrame frame = new JFrame("AAPFrame");
        frame.setContentPane(jpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!aaPlayer.getPlayerState()) {
                    JFileChooser jfc = new JFileChooser();
                    if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        File file = jfc.getSelectedFile();
                        String path = file.getAbsolutePath();
                        aaPlayer.play(path);
                    }
                }
            }
        });
        puginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc=new JFileChooser();
                if(jfc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                    File file=jfc.getSelectedFile();
                    String path = file.getAbsolutePath();
                    AAPlayer.addJarToXML(path);
                }
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(aaPlayer.getPlayerState()) {
                    aaPlayer.stop();
                }
            }
        });
        getListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    java.util.List<Plugin> list = aaPlayer.getPlugins();
                    String str = new String();
                    for(Plugin plugin: list){
                        str += (plugin + "\n");
                    }
                    JOptionPane.showMessageDialog(null, str, "插件列表",JOptionPane.PLAIN_MESSAGE);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, AboutBox.getHelp(), "帮助",JOptionPane.PLAIN_MESSAGE);
            }
        });
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, AboutBox.getInfo(), "关于",JOptionPane.PLAIN_MESSAGE);
            }
        });
    }

    public static void main(String[] args) throws Exception{
        AAPFrame aapFrame = new AAPFrame();
    }
}
