package com.cdruf.hellooptaplanner;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyApp {

    public static void main(String[] args) {
        SolverFactory<Plan> solverFactory = SolverFactory.create(new SolverConfig().withSolutionClass(Plan.class)
                                                                                   .withEntityClasses(MyEntity.class)
                                                                                   .withConstraintProviderClass(MyConstraintProvider.class)
                                                                                   .withTerminationSpentLimit(Duration.ofMinutes(1)));

        // Load the problem
//        TimeTable problem = generateDemoData();
        Plan problem = generateDemoData(10, 11);

        // Solve the problem
        Solver<Plan> solver = solverFactory.buildSolver();
        Plan solution = solver.solve(problem);

        solution.print();
    }

    public static Plan generateDemoData(int n_variables, int n_values) {
        List<MyVariableValue> values = IntStream.range(0, n_values).mapToObj(MyVariableValue::new)
                                                .collect(Collectors.toCollection(() -> new ArrayList<>(n_values)));

        List<MyEntity> variables = IntStream.range(0, n_variables).mapToObj(i -> new MyEntity((long) i))
                                            .collect(Collectors.toList());

        return new Plan(variables, values);
    }


}
