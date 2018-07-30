package com.matilda5g.cep.fusionEngine;

import org.json.JSONObject;

public class Netdata {

    // Initialize input variables from netdata stream
    private double cpuUsage;
    private double memUsage;
    private double diskUsage;
    private int packetsReceived;
    private int packetsSent;
    private double responseTime;
    private int httpRequestsReceived;
    private int httpRequestsIgnored;

    /*
    Need to find out the processes part...
     */

//    public Netdata(JSONObject response){
//        this.ramUsage = (double) response.get("system.ram")
//    }
    public void printResponse (JSONObject response){
        System.out.println(response.toString());
    }
}
