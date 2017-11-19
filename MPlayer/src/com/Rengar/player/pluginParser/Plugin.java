package com.Rengar.player.pluginParser;

public class Plugin {
    private String name;
    private String jar;
    private String type;
    private String className;

    public void setName(String name) {
        this.name = name;
    }

    public void setJar(String jar) {
        this.jar = jar;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public String getJar() {
        return jar;
    }

    public String getType() {
        return type;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString(){
        return name + "," + className;
    }
}