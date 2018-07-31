package com.matilda5g.cep.fusionEngine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Netdata {

    // Initialize input variables from netdata stream
    private double cpuUsage;
    private double memUsage;
    private double diskUsage;

    /*
    Need to find out the processes part...
     */

    public Netdata(JSONObject response) throws JSONException {
        JSONArray memUsage = response.getJSONArray("memUsage");
        System.out.println(memUsage.toString());
    }
    public void printResponse (JSONObject response){
        System.out.println(response.toString());
    }
}
