package com.Rengar.player;

import com.Rengar.Plugins.IPlayerPlugin;
import com.Rengar.player.pluginParser.Plugin;
import com.Rengar.player.pluginParser.PluginManager;
import com.Rengar.player.pluginParser.XMLParser;

import java.util.List;

public class AAPlayer {

    private boolean run = false;
    private String  path;
    private IPlayerPlugin playerPlugin;


    public boolean getPlayerState(){
        return run;
    }

    public static boolean addJarToXML(String pluginPath){
        try {
            if (XMLParser.exist(pluginPath) != null) {
                Plugin plugin = XMLParser.exist(pluginPath);
                if(XMLParser.addPlugin(plugin))
                    return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void play(String audioPath){
        path = audioPath;
        try {
            String[] token = audioPath.split("\\.");
            List<Plugin> pluginList = XMLParser.getPluginList();
            PluginManager pluginManager = new PluginManager(pluginList);
            for(Plugin plugin : pluginList){
                if(plugin.getType().equalsIgnoreCase(token[token.length - 1])){
                    playerPlugin = pluginManager.getInstance(plugin.getClassName());
                    playerPlugin.setFilePath(audioPath);
                    playerPlugin.service();
                    playerPlugin.play();
                    run = true;
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean stop() {
        if (run) {
            run = false;
            playerPlugin.stopAudio();
            return true;
        }else {
            return false;
        }
    }

    public List<Plugin> getPlugins() throws Exception{
        return  XMLParser.getPluginList();
    }
}
