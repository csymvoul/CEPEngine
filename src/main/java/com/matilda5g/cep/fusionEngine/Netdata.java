package com.matilda5g.cep.fusionEngine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Netdata {
    // Initialize input variables from netdata stream
    // cpu usage percentage
    private double cpuUsagePerc;
    // memory usage percentage
    private double memUsagePerc;
    // disk usage percentage
    private double diskUtilPerc;
    // disk size percentage
    private double diskSizePerc;
    // timestamp of the incoming data
    private long timeStamp;
    // uptime in seconds
    private long upTime;
    // IPv4 packets received, sent, delivered and forwarded
    private double ipv4PacketsReceived;
    private double ipv4PacketsSent;
    private double ipv4PacketsDelivered;
    private double ipv4PacketsForwarded;
    // IPv4 Kbps sent and received
    private double ipv4RSKbpsSent;
    private double ipv4RSKbpsReceived;
    // open IPv4 TCP connections/s
    private double ipv4TcpConnsOpenActive;
    private double ipv4TcpConnsOpenPassive;
    // IPv4 TCP connections/s aborts
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
        this.diskUtilPerc = getDoubleValue(response.getJSONArray("diskUsage")
                , "utilization");
        this.upTime = getLongValue(response.getJSONArray("uptime")
                , "uptime");
        this.ipv4PacketsReceived = getDoubleValue(response.getJSONArray("ipv4Packets")
                , "InReceives");
        this.ipv4PacketsDelivered = getDoubleValue(response.getJSONArray("ipv4Packets")
                , "InDelivers");
        this.ipv4PacketsForwarded = getDoubleValue(response.getJSONArray("ipv4Packets")
                , "ForwDatagrams");
        //the sent packets are negative so they are opposed in positive
        this.ipv4PacketsSent = getDoubleValue(response.getJSONArray("ipv4Packets")
                , "OutRequests") * -1;
        this.ipv4RSKbpsReceived = getDoubleValue(response.getJSONArray("ipv4RSKbps")
                , "InOctets");
        //the sent kbps are negative so they are opposed in positive
        this.ipv4RSKbpsSent = getDoubleValue(response.getJSONArray("ipv4RSKbps")
                , "OutOctets") * -1;
        this.ipv4TcpConnsOpenActive = getDoubleValue(response.getJSONArray("ipv4TcpConnsOpen")
                , "ActiveOpens");
        this.ipv4TcpConnsOpenPassive = getDoubleValue(response.getJSONArray("ipv4TcpConnsOpen")
                , "PassiveOpens");
        this.ipv4TcpConnsAbortsOnTimeout = getDoubleValue(response.getJSONArray("ipv4TcpConnsAborts")
                ,"TCPAbortOnTimeout");
        this.ipv4TcpConnsAbortsOnLinger = getDoubleValue(response.getJSONArray("ipv4TcpConnsAborts")
                ,"TCPAbortOnLinger");
        this.ipv4TcpConnsAbortsOnBadData = getDoubleValue(response.getJSONArray("ipv4TcpConnsAborts")
                ,"TCPAbortOnData");
        this.ipv4TcpConnsAbortsOnMemory = getDoubleValue(response.getJSONArray("ipv4TcpConnsAborts"),
                "TCPAbortOnMemory");
        this.ipv4TcpConnsAbortsFailed = getDoubleValue(response.getJSONArray("ipv4TcpConnsAborts")
                , "TCPAbortFailed");
        this.ipv4TcpConnsAbortsOnClose = getDoubleValue(response.getJSONArray("ipv4TcpConnsAborts")
                , "TCPAbortOnClose");

//        // print the inputs of the netdata object (debug)
//        printNetdataData();
    }

    private double getDoubleValue(JSONArray json, String field) throws JSONException {
        return json
                .getJSONObject(0)
                .getJSONObject("dimensions")
                .getJSONObject(field)
                .getDouble("value");
    }

    private long getLongValue(JSONArray json, String field) throws JSONException {
        return json
                .getJSONObject(0)
                .getJSONObject("dimensions")
                .getJSONObject(field)
                .getLong("value");
    }

    private long getTimeStamp(JSONArray memUsage) throws JSONException {
        return  memUsage
                .getJSONObject(0)
                .getLong("last_updated");
    }

    private double getMemUsagePerc(JSONArray memUsage) throws JSONException {

        // get the data from the jsonArray
        double cachedMem = getDoubleValue(memUsage, "cached");
        double bufferMem = getDoubleValue(memUsage, "buffers");
        double usedMem = getDoubleValue(memUsage, "used");
        double freeMem = getDoubleValue(memUsage, "free");

        // get the total memory
        double totalMem = cachedMem + bufferMem + usedMem + freeMem;
        // return the free memory percentage
        return freeMem * 100 / totalMem;
    }

    private double getCpuUsagePerc(JSONArray cpuUsage) throws JSONException {

        // get the data from the jsonArray
        double idleCpuPerc = getDoubleValue(cpuUsage, "idle");
        // return the cpu usage percentage
        return 100-idleCpuPerc;
    }

    private double getDiskSizePerc(JSONArray diskSize) throws JSONException {

        // get the data from the jsonArray
        double availDiskGb = getDoubleValue(diskSize, "avail");
        double usedDiskGb = getDoubleValue(diskSize, "used");
        double reservedForRootDiskGb = getDoubleValue(diskSize, "reserved_for_root");

        // get the total disk size
        double totalDiskSize = availDiskGb + usedDiskGb + reservedForRootDiskGb;

        // return the available memory percentage
        return availDiskGb * 100 / totalDiskSize;
    }

    public void printNetdataData() {
        System.out.println("*************************************");
        System.out.println(this.timeStamp);
        System.out.println("this.memUsagePerc: " + this.memUsagePerc);
        System.out.println("this.cpuUsagePerc" + this.cpuUsagePerc);
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
        System.out.println("*************************************");
    }

    public double getCpuUsagePerc() {
        return this.cpuUsagePerc;
    }

    public double getMemUsagePerc() {
        return this.memUsagePerc;
    }

    public double getDiskUtilPerc() {
        return this.diskUtilPerc;
    }

    public double getDiskSizePerc() {
        return this.diskSizePerc;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public long getUpTime() {
        return this.upTime;
    }

    public double getIpv4PacketsReceived() {
        return this.ipv4PacketsReceived;
    }

    public double getIpv4PacketsSent() {
        return this.ipv4PacketsSent;
    }

    public double getIpv4PacketsDelivered() {
        return this.ipv4PacketsDelivered;
    }

    public double getIpv4PacketsForwarded() {
        return this.ipv4PacketsForwarded;
    }

    public double getIpv4RSKbpsSent() {
        return this.ipv4RSKbpsSent;
    }

    public double getIpv4RSKbpsReceived() {
        return this.ipv4RSKbpsReceived;
    }

    public double getIpv4TcpConnsOpenActive() {
        return this.ipv4TcpConnsOpenActive;
    }

    public double getIpv4TcpConnsOpenPassive() {
        return this.ipv4TcpConnsOpenPassive;
    }

    public double getIpv4TcpConnsAbortsOnTimeout() {
        return this.ipv4TcpConnsAbortsOnTimeout;
    }

    public double getIpv4TcpConnsAbortsOnLinger() {
        return this.ipv4TcpConnsAbortsOnLinger;
    }

    public double getIpv4TcpConnsAbortsOnBadData() {
        return this.ipv4TcpConnsAbortsOnBadData;
    }

    public double getIpv4TcpConnsAbortsOnMemory() {
        return this.ipv4TcpConnsAbortsOnMemory;
    }

    public double getIpv4TcpConnsAbortsFailed() {
        return ipv4TcpConnsAbortsFailed;
    }

    public double getIpv4TcpConnsAbortsOnClose() {
        return this.ipv4TcpConnsAbortsOnClose;
    }
}
