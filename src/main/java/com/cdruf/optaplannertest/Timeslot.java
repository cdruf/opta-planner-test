package com.cdruf.optaplannertest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Timeslot {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * Short output to make the debug output of OptaPlanner more readable.
     */
    @Override
    public String toString() {
        return dayOfWeek + " " + startTime;
    }
}
