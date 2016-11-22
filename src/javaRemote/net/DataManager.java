package javaRemote.net;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Федя on 06.11.2016.
 */
public class DataManager implements IDataGetter {
    private BasicPlayer player;

    @Override
    public void onPlay() {
        try {
            String songName = "famous.wav";
            String pathToMp3 = System.getProperty("user.home") + "/Desktop/" + songName;
            player = new BasicPlayer();
            URL url = new URL("file:///" + pathToMp3);
            player.open(url);
            player.play();
        } catch (BasicPlayerException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        try {
            player.stop();
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    public void isPlaying() {
        System.out.println("status");

    }

}
