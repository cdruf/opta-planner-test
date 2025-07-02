package com.cdruf.cloudbalancing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@PlanningEntity
public class Process {
    @PlanningId
    private long id;

    private int requiredCpuPower;
    private int requiredMemory;
    private int requiredNetworkBandwidth;

    @PlanningVariable
    private Computer computer;

    @Override
    public String toString() {
        return "p_" + id;
    }


}
