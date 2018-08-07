package com.matilda5g.cep.streamingData;

import com.matilda5g.cep.fusionEngine.DroolsFusion;
import com.matilda5g.cep.fusionEngine.Netdata;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.json.JSONException;
import org.json.JSONObject;
import sun.nio.ch.Net;

public class HttpResponseListener {

    void onResponse(JSONObject httpResponse, String type) throws JSONException {
        if (type == "PROM"){
            //todo list
        } else if (type == "NETDATA"){
            // Create netdata object passing netdataResponse JSON
            Netdata netdata = createNetdataObject(httpResponse);

//            // Print netdata object (debug) to be sure I am retrieving it
//            System.out.println("from HttpResponseListener.onResponse");
//            netdata.printNetdataData();

            // Create DroolsFusion object
            DroolsFusion df = new DroolsFusion();
            // Set path of the rules file
            df.setRulesFilePath("rules/rules.drl");
            String rulesFilePath = df.getRulesFile().getPath();
            // Call droolsFusion method to initialize the cep process
            df.droolsFusionSession(netdata);

//            // Print rules file (debug) to be sure that I am getting it
//            df.printRules(rulesFilePath);
        }
    }

    private Netdata createNetdataObject(JSONObject httpResponse) throws JSONException{
        JSONObject netdataResponse = new JSONObject()
                .append("cpuUsage", new JSONObject(httpResponse.getJSONObject("system.cpu").toString()))
                .append("memUsage", new JSONObject(httpResponse.getJSONObject("system.ram").toString()))
                .append("diskSize", new JSONObject(httpResponse.getJSONObject("disk_space._").toString()))
                .append("diskUsage", new JSONObject(httpResponse.getJSONObject("disk_util.vda").toString()))
                .append("uptime", new JSONObject(httpResponse.getJSONObject("system.uptime").toString()))
                .append("ipv4Packets", new JSONObject(httpResponse.getJSONObject("ipv4.packets").toString()))
                .append("ipv4RSKbps", new JSONObject(httpResponse.getJSONObject("system.ipv4").toString()))
                .append("ipv4TcpConnsOpen", new JSONObject(httpResponse.getJSONObject("ipv4.tcpopens")
                        .toString()))
                .append("ipv4TcpConnsAborts", new JSONObject(httpResponse.getJSONObject("ipv4.tcpconnaborts")
                        .toString()));
        return new Netdata(netdataResponse);
    }
}
