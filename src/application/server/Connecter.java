package application.server;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public final class Connecter {

    private Connecter() {}

    private static final int CONN_TIMEOUT = 5000;

    private static HttpURLConnection createConnection(final String connURL) throws IOException {
        HttpURLConnection connection;
        URL url = new URL(connURL);
        connection = (HttpURLConnection) url.openConnection();
       // connection.setConnectTimeout(CONN_TIMEOUT);
        return connection;
    }

    private static String getInputStreamFromConnection(final HttpURLConnection connection) throws IOException {

        String inputStream = null;

            if (connection != null) {
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                while ((inputStream = br.readLine()) != null) {
                    sb.append(inputStream);
                }
                is.close();
                br.close();
                inputStream = sb.toString();
            }

        return inputStream;
    }

    public static String parseJSONFromConnection(String JSON, final String what_to_str) {

        try {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse("{" + "\"msg\" :" + JSON + "}");
            JSONArray items = (JSONArray) object.get("msg");
            JSONObject s = (JSONObject) items.get(0);
            JSON = s.get(what_to_str).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return JSON;
    }


    private static void closeConnection(HttpURLConnection connection) {
        connection.disconnect();
    }

    public static String getDataFromURL(final String url) throws SocketTimeoutException {
        String response = null;
        try {
            HttpURLConnection connection = createConnection(url);
            response = getInputStreamFromConnection(connection);
            closeConnection(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
