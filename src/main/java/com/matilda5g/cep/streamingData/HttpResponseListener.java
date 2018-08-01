package com.matilda5g.cep.streamingData;

import com.matilda5g.cep.fusionEngine.Netdata;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.json.JSONException;
import org.json.JSONObject;
import sun.nio.ch.Net;

public class HttpResponseListener {

    /*

        NETDATA Stream
            memory information (MB):                            system.ram,
            available memory  (MB):                             mem.available
            committed memory (MB):                              mem.committed

            cpu information (percentage):                       system.cpu

            active processes (# of processes):                  system.active_processes
            system uptime (seconds):                            system.uptime
            system io (kbps):                                   system.io
            system entropy (entropy):                           system.entropy

            disk space (GB):                                    disk_space._

            ipv4 received/sent kbps (kbps):                     system.ipv4
            ipv6 received/sent kbps (kbps):                     system.ipv6
            received/sent kbps (kbps):                          system.net
            ipv4 packets (packets/s):                           ipv4.packets
            ipv4 udp packets (packets/s):                       ipv4.udppackets
            ipv4 tcp handshakes (events/s):                     ipv4.tcpandshake
            ipv4 tcp connection aborts (connections/s):         ipv4.tcpconnaborts
            ipv4 open tcp connections (connections/s):          ipv4.tcpopens
            ipv4 tcp packets (packets/s):                       ipv4.tcppackets
     */

    void onResponse(JSONObject httpResponse, String type) throws JSONException {
        if (type == "PROM"){
//            System.out.println(httpResponse);
        } else if (type == "NETDATA"){

            //create netadata JSON to provide to the netdata object
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

            //create netdata object passing netdataResponse JSON
            Netdata netdata = new Netdata(netdataResponse);
        }
    }
}
