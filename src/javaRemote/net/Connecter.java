package javaRemote.net;

/**
 * Created by Федя on 06.11.2016.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connecter implements IConnecter {

    private URL connURL;
    private HttpURLConnection connection;


    @Override
    public void createConnection(final String myURL, Callback callback) throws MalformedURLException {
        try {
            connURL = new URL(myURL);
            connection = (HttpURLConnection) connURL.openConnection();
            connection.setConnectTimeout(2000);

            String ansver;
            if (connection != null) {
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                while ((ansver = br.readLine()) != null) {
                    sb.append(ansver);
                }
                is.close();
                br.close();

                ansver = sb.toString();


                if (ansver.equals("no signal\t\t")) {
                    callback.onError();
                } else if (ansver.equals("got signal\t\t")) {
                    callback.onSuccess();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    URL oracle = new URL("http://www.oracle.com/");
//    BufferedReader in = new BufferedReader(
//            new InputStreamReader(oracle.openStream()));
//
//    String inputLine;
//    while ((inputLine = in.readLine()) != null)
//            System.out.println(inputLine);
//    in.close();



}
