package com.matilda5g.cep.fusionEngine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.nio.ch.Net;

public class Netdata {

    // Initialize input variables from netdata stream
    //cpu usage percentage
    private double cpuUsagePerc;
    //memory usage percentage
    private double memUsagePerc;
    //disk usage percentage
    private double diskUtilPerc;
    //disk size percentage
    private double diskSizePerc;
    //timestamp of the incoming data
    private long timeStamp;
    //uptime in seconds
    private long upTime;
    //IPv4 packets received, sent, delivered and forwarded
    private double ipv4PacketsReceived;
    private double ipv4PacketsSent;
    private double ipv4PacketsDelivered;
    private double ipv4PacketsForwarded;
    //IPv4 Kbps sent and received
    private double ipv4RSKbpsSent;
    private double ipv4RSKbpsReceived;
    //open IPv4 TCP connections/s
    private double ipv4TcpConnsOpenActive;
    private double ipv4TcpConnsOpenPassive;
    //IPv4 TCP connections/s aborts
    private double ipv4TcpConnsAbortsOnTimeout;
    private double ipv4TcpConnsAbortsOnLinger;
    private double ipv4TcpConnsAbortsOnBadData;
    private double ipv4TcpConnsAbortsOnMemory;
    private double ipv4TcpConnsAbortsFailed;
    private double ipv4TcpConnsAbortsOnClose;





    public Netdata(JSONObject response) throws JSONException {

        //create a separate jsonArray for each data we will use
//        JSONArray memUsage = response.getJSONArray("memUsage");
//        JSONArray cpuUsage = response.getJSONArray("cpuUsage");
//        JSONArray diskUtil = response.getJSONArray("diskUsage");
//        JSONArray diskSize = response.getJSONArray("diskSize");
//        JSONArray uptime = response.getJSONArray("uptime");
//        JSONArray ipv4Packets = response.getJSONArray("ipv4Packets");
//        JSONArray ipv4RSKbps = response.getJSONArray("ipv4RSKbps");
//        JSONArray ipv4TcpConnsOpen = response.getJSONArray("ipv4TcpConnsOpen");
//        JSONArray ipv4TcpConnsAborts = response.getJSONArray("ipv4TcpConnsAborts");

        //initialization of the Netdata object
        this.timeStamp = getTimeStamp(response.getJSONArray("memUsage"));
        this.memUsagePerc = getMemUsagePerc(response.getJSONArray("memUsage"));
        this.cpuUsagePerc = getCpuUsagePerc(response.getJSONArray("cpuUsage"));
        this.diskSizePerc = getDiskSizePerc(response.getJSONArray("diskSize"));
        this.diskUtilPerc = getDoubleValue(response.getJSONArray("diskUsage"), "utilization");
        this.upTime = getLongValue(response.getJSONArray("uptime"), "uptime");
        this.ipv4PacketsReceived = getDoubleValue(response.getJSONArray("ipv4Packets"), "InReceives");
        this.ipv4PacketsDelivered = getDoubleValue(response.getJSONArray("ipv4Packets"), "InDelivers");
        this.ipv4PacketsForwarded = getDoubleValue(response.getJSONArray("ipv4Packets"), "ForwDatagrams");
        this.ipv4PacketsSent = getDoubleValue(response.getJSONArray("ipv4Packets"), "OutRequests") * -1;
        this.ipv4RSKbpsReceived = getDoubleValue(response.getJSONArray("ipv4RSKbps"), "InOctets");
        this.ipv4RSKbpsSent = getDoubleValue(response.getJSONArray("ipv4RSKbps"), "OutOctets") * -1;
        this.ipv4TcpConnsOpenActive = getDoubleValue(response.getJSONArray("ipv4TcpConnsOpen"), "ActiveOpens");
        this.ipv4TcpConnsOpenPassive = getDoubleValue(response.getJSONArray("ipv4TcpConnsOpen"), "PassiveOpens");
        this.ipv4TcpConnsAbortsOnTimeout = getDoubleValue(response.getJSONArray("ipv4TcpConnsAborts"), "TCPAbortOnTimeout");
        this.ipv4TcpConnsAbortsOnLinger = getDoubleValue(response.getJSONArray("ipv4TcpConnsAborts"), "TCPAbortOnLinger");
        this.ipv4TcpConnsAbortsOnBadData = getDoubleValue(response.getJSONArray("ipv4TcpConnsAborts"), "TCPAbortOnData");
        this.ipv4TcpConnsAbortsOnMemory = getDoubleValue(response.getJSONArray("ipv4TcpConnsAborts"), "TCPAbortOnMemory");
        this.ipv4TcpConnsAbortsFailed = getDoubleValue(response.getJSONArray("ipv4TcpConnsAborts"), "TCPAbortFailed");
        this.ipv4TcpConnsAbortsOnClose = getDoubleValue(response.getJSONArray("ipv4TcpConnsAborts"), "TCPAbortOnClose");

        System.out.println(this.timeStamp);
        System.out.println(this.memUsagePerc);
        System.out.println(this.cpuUsagePerc);
        System.out.println(this.diskSizePerc);
        System.out.println(this.diskUtilPerc);
        System.out.println(this.upTime);
        System.out.println(this.ipv4PacketsReceived);
        System.out.println(this.ipv4PacketsDelivered);
        System.out.println(this.ipv4PacketsForwarded);
        System.out.println(this.ipv4PacketsSent);
        System.out.println(this.ipv4RSKbpsReceived);
        System.out.println(this.ipv4RSKbpsSent);
        System.out.println(this.ipv4TcpConnsOpenActive);
        System.out.println(this.ipv4TcpConnsOpenPassive);
        System.out.println(this.ipv4TcpConnsAbortsOnTimeout);
        System.out.println(this.ipv4TcpConnsAbortsOnLinger);
        System.out.println(this.ipv4TcpConnsAbortsOnBadData);
        System.out.println(this.ipv4TcpConnsAbortsOnMemory);
        System.out.println(this.ipv4TcpConnsAbortsFailed);
        System.out.println(this.ipv4TcpConnsAbortsOnClose);


    }

    private double getDoubleValue(JSONArray json, String field) throws JSONException {
        double value = json
                .getJSONObject(0)
                .getJSONObject("dimensions")
                .getJSONObject(field)
                .getDouble("value");

        return value;
    }

    private long getLongValue(JSONArray json, String field) throws JSONException {
        long value = json
                .getJSONObject(0)
                .getJSONObject("dimensions")
                .getJSONObject(field)
                .getLong("value");

        return value;
    }

    private long getTimeStamp(JSONArray memUsage) throws JSONException {
        long stamp = memUsage
                .getJSONObject(0)
                .getLong("last_updated");
        return stamp;
    }

    private double getMemUsagePerc(JSONArray memUsage) throws JSONException {

        // get the data from the jsonArray
        double cachedMem = getDoubleValue(memUsage, "cached");
        double bufferMem = getDoubleValue(memUsage, "buffers");
        double usedMem = getDoubleValue(memUsage, "used");
        double freeMem = getDoubleValue(memUsage, "free");

        //get the total memory
        double totalMem = cachedMem + bufferMem + usedMem + freeMem;
        //return the free memory percentage
        return freeMem * 100 / totalMem;
    }

    private double getCpuUsagePerc(JSONArray cpuUsage) throws JSONException {

        // get the data from the jsonArray
        double idleCpuPerc = getDoubleValue(cpuUsage, "idle");
        //return the cpu usage percentage
        return 100-idleCpuPerc;
    }

    private double getDiskSizePerc(JSONArray diskSize) throws JSONException {

        // get the data from the jsonArray
        double availDiskGb = getDoubleValue(diskSize, "avail");
        double usedDiskGb = getDoubleValue(diskSize, "used");
        double reservedForRootDiskGb = getDoubleValue(diskSize, "reserved_for_root");

        //get the total disk size
        double totalDiskSize = availDiskGb + usedDiskGb + reservedForRootDiskGb;

        //return the available memory percentage
        return availDiskGb * 100 / totalDiskSize;
    }

//    private long getUptime(JSONArray uptime) throws JSONException {
//        long upTime = uptime
//                .getJSONObject(0)
//                .getJSONObject("dimensions")
//                .getJSONObject("uptime")
//                .getLong("value");
//
//        return upTime;
//    }

//    private double getDiskUtilPerc(JSONArray diskUtil) throws JSONException {
////        System.out.println(diskUsage.toString());
//
//        double diskUtilPerc = diskUtil
//                .getJSONObject(0)
//                .getJSONObject("dimensions")
//                .getJSONObject("utilization")
//                .getDouble("value");
//        return diskUtilPerc;
//    }

}
