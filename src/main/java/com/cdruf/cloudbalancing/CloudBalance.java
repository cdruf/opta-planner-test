package com.cdruf.cloudbalancing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@PlanningSolution
public class CloudBalance {
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Computer> computerList;

    @PlanningEntityCollectionProperty
    private List<Process> processList;

    @PlanningScore
    private HardSoftScore score;

    public CloudBalance(List<Computer> computerList, List<Process> processList) {
        this.computerList = computerList;
        this.processList = processList;
    }

    public void print() {
        System.out.println("Score = " + score);
        for (Process p : processList) {
            System.out.println(p + " -> " + p.getComputer());
        }
    }

}
