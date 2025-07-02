package com.cdruf.nqueens;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.simple.SimpleScore;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@PlanningSolution
public class NQueens {
    private int n; // == number of columns == number of rows
    // we do not need a column list because we ensure that each queen has a fixed column

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Row> rowList;

    @PlanningEntityCollectionProperty
    private List<Queen> queenList;

    @PlanningScore
    private SimpleScore score;


    public NQueens(int n) {
        this.n = n;
        rowList = new ArrayList<>();
        queenList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            rowList.add(new Row(i));
            queenList.add(new Queen(i));
        }
    }

    public void print() {
        System.out.println("Score = " + score);
        for (Queen q : queenList) {
            System.out.println(q + " @ " + q.getRow().getIndex() + "," + q.getColumn().getIndex());
        }

        // Create the grid
        char[][] grid = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = '.';
            }
        }
        for (Queen queen : queenList) {
            if (queen.getRow() != null) {
                grid[queen.getRow().getIndex()][queen.getColumn().getIndex()] = 'Q';
            }
        }

        // Print the grid
        System.out.print("  ");
        for (int j = 0; j < n; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < n; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
