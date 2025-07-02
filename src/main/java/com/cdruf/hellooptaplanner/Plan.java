package com.cdruf.hellooptaplanner;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@NoArgsConstructor
@Getter
@PlanningSolution
public class Plan {

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<MyVariableValue> variableValues;


    @PlanningEntityCollectionProperty
    private List<MyEntity> variables;

    @PlanningScore
    private HardSoftScore score;


    public Plan(List<MyEntity> variableList, List<MyVariableValue> variableValues) {
        this.variables = variableList;
        this.variableValues = variableValues;
    }

    public void print() {
        System.out.println("Score: " + score);
        for (MyEntity variable : variables) {
            System.out.println("Variable " + variable.getId() + " = " + variable.getX());
        }
    }
}
