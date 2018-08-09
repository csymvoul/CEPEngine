package com.matilda5g.cep.fusionEngine;

public class NetdataBucket {

    /*
        This NetdataBucket class is used in Drools Fusion to identify whether one or more attributes of the system
        (e.g. CPU usage, Memory usage, Disk utilization, etc.) exceed a specified threshold as shown in the rules
        (/rules/rules.drl).

        We are collecting data from the VM per 5 seconds. So, if the CPU usage is over 15% the cpuCount is raised by
        one, but when it drops that threshold, cpuCount turns back to zero. The rule for the CPU is fired only when
        the cpuCount is greater than, or equal to 2, meaning for the last 10 seconds.

        The same goes by for memory, disk, etc. as well.
    */

    public static int cpuCountOver = 0;
    public static int cpuCountLow = 0;
    public static int memCountOver = 0;
    public static int memCountLow = 0;
    public static int diskCountOver = 0;
    public static int diskCountLow = 0;

    // CPU usage related methods
    // Great CPU Usage
    public void setCpuCountOver() {
        cpuCountOver++;
        System.out.println("Current cpuCountOver: " +  cpuCountOver);
    }

    public int getCpuCountOver() {
        return cpuCountOver;
    }

    public void setCpuCountOverToZero() {
        cpuCountOver=0;
        System.out.println("Current cpuCountOver: " +  cpuCountOver);
    }

    // Low CPU Usage
    public void setCpuCountLow() {
        cpuCountLow++;
        System.out.println("Current cpuCountLow: " +  cpuCountLow);
    }

    public int getCpuCountLow() {
        return cpuCountLow;
    }

    public void setCpuCountLowToZero() {
        cpuCountLow=0;
        System.out.println("Current cpuCountLow: " +  cpuCountLow);
    }

    // Memory usage related methods
    // Great Memory Usage
    public void setMemCountOver() {
        memCountOver++;
        System.out.println("Current memCountOver: " + memCountOver);
    }

    public void setMemCountOverToZero() {
        memCountOver=0;
        System.out.println("Current memCountOver: " + memCountOver);
    }

    public int getMemCountOver() {
        return memCountOver;
    }

    //Low Memory Usage
    public void setMemCountLow() {
        memCountLow++;
        System.out.println("Current memCountLow: " + memCountLow);
    }

    public void setMemCountLowToZero() {
        memCountLow=0;
        System.out.println("Current memCountLow: " + memCountLow);
    }

    public int getMemCountLow() {
        return memCountLow;
    }


    // Disk utilization related methods
    // Great Disk utilization
    public void setDiskCountOver() {
        diskCountOver++;
        System.out.println("Current diskCountOver: " + diskCountOver);
    }

    public void setDiskCountOverToZero() {
        diskCountOver=0;
        System.out.println("Current diskCountOver: " + diskCountOver);
    }

    public int getDiskCountOver() {
        return diskCountOver;
    }

    // Low Disk utilization
    public void setDiskCountLow() {
        diskCountLow++;
        System.out.println("Current diskCountLow: " + diskCountLow);
    }

    public void setDiskCountLowToZero() {
        diskCountLow=0;
        System.out.println("Current diskCountLow: " + diskCountLow);
    }

    public int getDiskCountLow() {
        return diskCountLow;
    }

}
