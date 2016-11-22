package javaRemote.net;

import java.net.MalformedURLException;

/**
 * Created by Федя on 06.11.2016.
 */
public interface IConnecter {

    void createConnection(final String URL, Callback callback) throws MalformedURLException;

}
