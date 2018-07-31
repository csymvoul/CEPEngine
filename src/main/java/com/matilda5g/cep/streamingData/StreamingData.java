package com.matilda5g.cep.streamingData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.*;
import javax.xml.ws.http.HTTPException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import java.util.Calendar;

public class StreamingData {

    private String url;
    private String type;
    private volatile JSONObject httpResponse;

    public StreamingData(String url, String type){
        this.url = url;
        this.type = type;
    }

    public void httpRequest() throws Exception{
        try {
            URL obj = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional, since the default method is GET
            con.setRequestMethod("GET");

            // the response code of the httpRequest (e.g. 400, 200, etc.)
//            int responseCode = con.getResponseCode();
//            System.out.println("\nSending 'GET' request to URL : " + url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            setHttpResponse(new JSONObject(response.toString()));

        } catch (ConnectException e){
            System.out.println("Connection timeout");

        } catch (HTTPException e){
            System.out.println("HTTPException");
        }
//        return null;
    }

    public void setHttpResponse(JSONObject httpResponse) throws JSONException {
        this.httpResponse = httpResponse;
        HttpResponseListener listener = new HttpResponseListener();
        listener.onResponse(this.httpResponse, this.type);
    }

//    public JSONObject getHttpResponse() {
//        System.out.println(this.httpResponse.toString());
//        return this.httpResponse;
//    }

    public void StreamHttpRequest() {
        final long timeInterval = 1000;
        Runnable runnable = new Runnable() {

//        final Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
            public void run() {
                while (true) {
                    try {
                        httpRequest();
//                        setHttpResponse(httpResponse);
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
//        }, 0, 5000);

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
