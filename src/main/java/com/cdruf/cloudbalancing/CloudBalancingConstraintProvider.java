package com.cdruf.cloudbalancing;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import java.util.function.Function;

import static org.optaplanner.core.api.score.stream.ConstraintCollectors.sum;
import static org.optaplanner.core.api.score.stream.Joiners.equal;

public class CloudBalancingConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory factory) {
        return new Constraint[]{requiredCpuPowerTotal(factory), //
                requiredMemoryTotal(factory),//
                requiredNetworkBandwidthTotal(factory),//
                computerCost(factory)};
    }

    // ************************************************************************
    // Hard constraints
    // ************************************************************************

    Constraint requiredCpuPowerTotal(ConstraintFactory factory) {
        return factory.forEach(Process.class).groupBy(Process::getComputer, sum(Process::getRequiredCpuPower))
                      .filter((computer, requiredCpuPower) -> requiredCpuPower > computer.getCpuPower())
                      .penalize(HardSoftScore.ONE_HARD,
                              (computer, requiredCpuPower) -> requiredCpuPower - computer.getCpuPower())
                      .asConstraint("requiredCpuPowerTotal");
    }

    Constraint requiredMemoryTotal(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Process.class).groupBy(Process::getComputer, sum(Process::getRequiredMemory))
                                .filter((computer, requiredMemory) -> requiredMemory > computer.getMemory())
                                .penalize(HardSoftScore.ONE_HARD,
                                        (computer, requiredMemory) -> requiredMemory - computer.getMemory())
                                .asConstraint("requiredMemoryTotal");
    }

    Constraint requiredNetworkBandwidthTotal(ConstraintFactory factory) {
        return factory.forEach(Process.class).groupBy(Process::getComputer, sum(Process::getRequiredNetworkBandwidth))
                      .filter((computer, requiredNetworkBandwidth) -> requiredNetworkBandwidth > computer.getNetworkBandwidth())
                      .penalize(HardSoftScore.ONE_HARD,
                              (computer, requiredNetworkBandwidth) -> requiredNetworkBandwidth - computer.getNetworkBandwidth())
                      .asConstraint("requiredNetworkBandwidthTotal");
    }

    // ************************************************************************
    // Soft constraints
    // ************************************************************************

    Constraint computerCost(ConstraintFactory factory) {
        return factory.forEach(Computer.class).ifExists(Process.class, equal(Function.identity(), Process::getComputer))
                      .penalize(HardSoftScore.ONE_SOFT, Computer::getCost).asConstraint("computerCost");
    }

}
