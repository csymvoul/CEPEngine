package com.matilda5g.cep.fusionEngine;

public class NetdataBucket {
    public static int cpuCount = 0;
    public static int memCount = 0;
    public static int diskCount = 0;
    /*
        This NetdataBucket class is used in Drools Fusion to identify whether one or more attributes of the system
        (e.g. CPU usage, Memory usage, Disk utilization, etc.) exceed a specified threshold as shown in the rules
        (/rules/rules.drl).

        We are collecting data from the VM per 5 seconds. So, if the CPU usage is over 15% the cpuCount is raised by
        one, but when it drops that threshold, cpuCount turns back to zero. The rule for the CPU is fired only when
        the cpuCount is greater than, or equal to 2, meaning for the last 10 seconds.

        The same goes by for memory, disk, etc. as well. 
    */
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
