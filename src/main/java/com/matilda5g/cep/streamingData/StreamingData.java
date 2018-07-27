package com.matilda5g.cep.streamingData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;
import javax.xml.ws.http.HTTPException;

public class StreamingData {

    private String url;

    public StreamingData(String url){
        this.url = url;
    }

    private void httpRequest() throws Exception{
        try {
            URL obj = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional, since the default method is GET
            con.setRequestMethod("GET");

            // optional, add request header
//            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
//            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

//            System.out.println(response.toString());

            // Read JSON response and print
//            JSONObject myResponse = new JSONObject(response.toString());
//            System.out.println(myResponse);
        } catch (ConnectException e){
            System.out.println("Connection timeout");
        } catch (HTTPException e){
            System.out.println("HTTPException");
        }
    }

    public void StreamHttpRequest() {
        final long timeInterval = 1000;
        Runnable runnable = new Runnable() {
//            @Override
            public void run() {
                while(true){
                    try {
                        httpRequest();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
