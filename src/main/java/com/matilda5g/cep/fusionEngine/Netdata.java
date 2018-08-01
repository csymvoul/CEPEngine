package com.matilda5g.cep.fusionEngine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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



    public Netdata(JSONObject response) throws JSONException {


        //create a separate jsonArray for each data we will use
        JSONArray memUsage = response.getJSONArray("memUsage");
        JSONArray cpuUsage = response.getJSONArray("cpuUsage");
        JSONArray diskUtil = response.getJSONArray("diskUsage");
        JSONArray diskSize = response.getJSONArray("diskSize");
        JSONArray uptime = response.getJSONArray("uptime");
        JSONArray ipv4Packets = response.getJSONArray("ipv4Packets");
        JSONArray ipv4RSKbps = response.getJSONArray("ipv4RSKbps");
        JSONArray ipv4TcpConnsOpen = response.getJSONArray("ipv4TcpConnsOpen");
        JSONArray ipv4TcpConnsAborts = response.getJSONArray("ipv4TcpConnsAborts");

        this.timeStamp = getTimeStamp(memUsage);
        this.memUsagePerc = getMemUsagePerc(memUsage);;
        this.cpuUsagePerc = getCpuUsagePerc(cpuUsage);
        this.diskSizePerc = getDiskSizePerc(diskSize);
        this.diskUtilPerc = getDoubleValue(diskUtil, "utilization");
        this.upTime = getLongValue(uptime, "uptime");
        this.ipv4PacketsReceived = getDoubleValue(ipv4Packets, "InReceives");
        this.ipv4PacketsDelivered = getDoubleValue(ipv4Packets, "InDelivers");
        this.ipv4PacketsForwarded = getDoubleValue(ipv4Packets, "ForwDatagrams");
        this.ipv4PacketsSent = getDoubleValue(ipv4Packets, "OutRequests") * -1;

//        System.out.println(this.timeStamp);
//        System.out.println(this.memUsagePerc);
//        System.out.println(this.cpuUsagePerc);
//        System.out.println(this.diskSizePerc);
//        System.out.println(this.diskUtilPerc);
//        System.out.println(this.upTime);


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

}
