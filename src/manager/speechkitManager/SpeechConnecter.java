package manager.speechkitManager;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Федя on 24.11.2016.
 */
public class SpeechConnecter {

    private static URL connURL;
    private static HttpURLConnection connection;

    public static void playSpeechFromURL(final String speechUrl) {
        BasicPlayer player = new BasicPlayer();
        try {
            connURL = new URL(speechUrl);
            player.open(connURL);
            player.play();
        } catch (BasicPlayerException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
