package com.cdruf.optaplannertest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@NoArgsConstructor
@Getter
@PlanningEntity
public class Lesson {

    @PlanningId
    private Long id;

    private String subject;
    private String teacher;
    private String studentGroup;

    @Setter
    @PlanningVariable
    private Timeslot timeslot;

    @Setter
    @PlanningVariable
    private Room room;

    public Lesson(Long id, String subject, String teacher, String studentGroup) {
        this.id = id;
        this.subject = subject;
        this.teacher = teacher;
        this.studentGroup = studentGroup;
    }


    @Override
    public String toString() {
        return subject + "(" + id + ")";
    }

}