package com.Rengar.player.pluginParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLParser {
    public static List<Plugin> getPluginList() throws DocumentException {
        List<Plugin> list = new ArrayList<Plugin>();

        SAXReader saxReader =new SAXReader();
        Document document = saxReader.read(new File("plugin.xml"));
        Element root = document.getRootElement();
        List<?> plugins = root.elements("plugin");
        for(Object pluginObj : plugins) {
            Element pluginEle = (Element)pluginObj;
            Plugin plugin = new Plugin();
            plugin.setName(pluginEle.elementText("name"));
            plugin.setJar(pluginEle.elementText("jar"));
            plugin.setType(pluginEle.elementText("type"));
            plugin.setClassName(pluginEle.elementText("class"));
            list.add(plugin);
        }
        return list;
    }

    public static boolean addPlugin(Plugin plugin) {
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new File("plugin.xml"));
            Element root = document.getRootElement();
            Element pluginEle = root.addElement("plugin");
            Element name = pluginEle.addElement("name");
            name.setText(plugin.getName());
            Element jar = pluginEle.addElement("jar");
            jar.setText(plugin.getJar());
            Element className = pluginEle.addElement("class");
            className.setText(plugin.getClassName());
            Element type = pluginEle.addElement("type");
            type.setText(plugin.getType());
            OutputFormat opf=new OutputFormat("\t",true,"UTF-8");
            opf.setTrimText(true);
            XMLWriter writer=new XMLWriter(new FileOutputStream("plugin.xml"),opf);
            writer.write(document);
            writer.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static Plugin exist(String filePath){
        try{
            SAXReader saxReader = new SAXReader();
            File file = new File("plugin.xml");
            if(!file.exists()){
                file.createNewFile();
            }
            Document document = saxReader.read(file);
            Element root = document.getRootElement();
            List<?> plugins = root.elements("plugin");
            for(Object pluginObj : plugins) {
                Element pluginEle = (Element)pluginObj;
                Plugin plugin = new Plugin();
                plugin.setJar(filePath);
                JarFile jarFile = new JarFile(filePath);
                Enumeration enu = jarFile.entries();
                while (enu.hasMoreElements()) {
                    JarEntry entry = (JarEntry)enu.nextElement();
                    String name = entry.getName();
                    if(name.equals("lib/plugin.xml")){
                        InputStream input = jarFile.getInputStream(entry);
                        SAXReader reader = new SAXReader();
                        Document document1 = reader.read(input);
                        Element r = document1.getRootElement();
                        plugin.setClassName(r.elementText("class"));
                        plugin.setType(r.elementText("type"));
                        plugin.setName(r.elementText("name"));
                        if(plugin.getClassName().equals(pluginEle.elementText("class"))){
                            return null;
                        }
                        break;
                    }
                }
                jarFile.close();
                return plugin;
            }if(plugins.size() == 0){
                Plugin plugin = new Plugin();
                plugin.setJar(filePath);
                plugin.setClassName("com.Rengar.Plugins.mp3.MP3PlayerPlugin");
                plugin.setName("MP3PlayerPlugin");
                plugin.setType("mp3");
                return plugin;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
