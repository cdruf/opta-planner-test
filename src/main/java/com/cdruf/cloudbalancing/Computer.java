package com.cdruf.cloudbalancing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Computer {
    private long id;
    private int cpuPower;
    private int memory;
    private int networkBandwidth;
    private int cost;

    @Override
    public String toString() {
        return "c_" + id;
    }

}
