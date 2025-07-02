package com.cdruf.cloudbalancing;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;

import java.time.Duration;

public class CloudBalanceApp {
    public static void main(String[] args) {
        SolverFactory<CloudBalance> solverFactory = SolverFactory.create(new SolverConfig()
                .withSolutionClass(CloudBalance.class).withEntityClasses(Process.class)
                .withConstraintProviderClass(CloudBalancingConstraintProvider.class)
                .withTerminationSpentLimit(Duration.ofMinutes(2)));
        CloudBalance problem = new CloudBalancingGenerator().createCloudBalance(400, 1200);

        // Solve
        Solver<CloudBalance> solver = solverFactory.buildSolver();
        CloudBalance solution = solver.solve(problem);

        solution.print();

    }
}
