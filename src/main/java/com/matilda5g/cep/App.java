package com.matilda5g.cep;

import com.matilda5g.cep.streamingData.HttpResponseListener;
import com.matilda5g.cep.streamingData.StreamingData;
import org.json.JSONObject;

public class App {
    public static void main( String[] args ) {

        // Create a StreamingData object with two parameters: (String url, String type), where type can be either NETDATA, PROM, PROMALERTS)
        StreamingData netDataStream = new StreamingData("http://83.212.96.238:19999/api/v1/allmetrics?format=json", "NETDATA");

//        JSONObject netDataHttpResponse = netDataStream.getHttpResponse();


//        StreamingData cpuUsageStream = new StreamingData("http://83.212.96.238:9090/api/v1/query?query=(sum(rate(node_cpu[1m]))>0.01)", "PROM");

        netDataStream.StreamHttpRequest();
//        cpuUsageStream.StreamHttpRequest();
    }
}
