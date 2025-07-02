package com.cdruf.hellooptaplanner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@NoArgsConstructor
@Getter
@PlanningEntity
public class MyEntity {
    @PlanningId
    private Long id;

    @Setter
    @PlanningVariable
    private MyVariableValue x;

    public MyEntity(Long id) {
        this.id = id;
    }

}
