# MPlayer
基于Java的多态性实现一个模拟的播放器插件程序.<br>
（1）宿主程序实现：可基于Java AWT框架搭建软件界面，由AAPlayer、AAPFrame、AboutBox三个Java类构成，AAPlayer是整个程序的主类，负责构建AAPFrame类的实例，进行基本的配置等。AAPFrame 类负责初始化音频播放器界面，选择加载音频播放插件，为相关按钮添加事件，控制音频的播放等操作。AboutBox用来介绍软件信息；<br>
（2）插件实现：模拟音频播放器的播放插件都是基于接口IPlayerPlugin实现的，包括MP3PlayerPlugin、WAVPlayerPlugin、OGGPlayerPlugin等，用户还可以根据自己的需求，去扩展不同文件格式的播放插件。
