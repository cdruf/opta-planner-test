package com.cdruf.nqueens;

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
public class Queen {
    @PlanningId
    private long id;
    /**
     * The column is fixed because we know that each column must have a queen.
     */
    private Col column;

    @PlanningVariable
    private Row row;


    public Queen(long id) {
        this.id = id;
        this.column = new Col((int) id);
    }

    /**
     * Identifies the diagonal among the ascending diagonals (from left bottom to right top).
     * Logic: Assume we start at (0, 0).
     * Every step right moves us to the next diagonal.
     * Every step down moves us to the next diagonal.
     * The numbers go from 0 to 2n-1.
     */
    public int getAscendingDiagonalIndex() {
        return column.getIndex() + row.getIndex();

    }

    /**
     * Identifies the diagonal among the descending diagonals.
     * Logic: Assume we start at (0, 0).
     * Every step right moves us to the next diagonal. Every step left moves us to the previous diagonal.
     * Every step down moves us to the previous diagonal. Every step up moves us to the next diagonal.
     * The numbers go from -(n-1) to n-1.
     */
    public int getDescendingDiagonalIndex() {
        return column.getIndex() - row.getIndex();
    }

    @Override
    public String toString() {
        return "Q_" + id;
    }
}
