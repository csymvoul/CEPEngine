package com.matilda5g.cep.streamingData;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.json.JSONException;
import org.json.JSONObject;

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
            System.out.println(httpResponse);
        } else if (type == "NETDATA"){
            // CPU information
            // percentage
            JSONObject cpuUsage = new JSONObject(httpResponse.getJSONObject("system.cpu"));

            // Memory information
            //MB
            JSONObject memUsage = new JSONObject(httpResponse.getJSONObject("system.ram"));

            // Disk information
            // GB
            JSONObject diskSize = new JSONObject(httpResponse.getJSONObject("disk_space._"));
            // kbps
            JSONObject diskUsage = new JSONObject(httpResponse.getJSONObject("system.io"));

            // General system information
            //seconds
            JSONObject upTime = new JSONObject(httpResponse.getJSONObject("system.uptime"));

            // Networking information
            JSONObject ipv4RSKbps = new JSONObject(httpResponse.getJSONObject("system.ipv4"));
            JSONObject ipv4TcpConnsOpen = new JSONObject(httpResponse.getJSONObject("ipv4.tcpopens"));
            JSONObject ipv4TcpConnsAborts = new JSONObject(httpResponse.getJSONObject("ipv4.tcpconnaborts"));

            //

        }

    }
}
