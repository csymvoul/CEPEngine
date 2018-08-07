package com.matilda5g.cep.fusionEngine;

public class NetdataBucket {
    public static int cpuCount = 0;
    public static int memCount = 0;
    public static int diskCount = 0;

    public void setCpuCount() {
        cpuCount++;
        System.out.println("Current cpuCount: " +  cpuCount);
    }

    public void setCpuCountToZero() {
        cpuCount=0;
        System.out.println("Current cpuCount: " +  cpuCount);
    }

    public int getCpuCount() {
        return cpuCount;
    }

    public void setMemCount() {
        memCount++;
        System.out.println("Current memCount: " + memCount);
    }

    public void setMemCountToZero() {
        memCount=0;
        System.out.println("Current memCount: " + memCount);
    }

    public int getMemCount() {
        return memCount;
    }

    public void setDiskCount() {
        diskCount++;
        System.out.println("Current memCount: " + diskCount);
    }

    public void setDiskCountToZero() {
        diskCount=0;
        System.out.println("Current memCount: " + diskCount);
    }

    public int getDiskCount() {
        return diskCount;
    }

}
