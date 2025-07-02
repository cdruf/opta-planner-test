package com.cdruf.nqueens;

import com.cdruf.hellooptaplanner.Plan;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;

import java.time.Duration;

public class NQueensApp {
    public static void main(String[] args) {
        SolverFactory<NQueens> solverFactory = SolverFactory.create(new SolverConfig().withSolutionClass(NQueens.class)
                                                                                   .withEntityClasses(Queen.class)
                                                                                   .withConstraintProviderClass(NQueensConstraintProvider.class)
                                                                                   .withTerminationSpentLimit(Duration.ofMinutes(1)));
        NQueens problem = new NQueens(4);

        // Solve the problem
        Solver<NQueens> solver = solverFactory.buildSolver();
        NQueens solution = solver.solve(problem);

        solution.print();

    }
}
