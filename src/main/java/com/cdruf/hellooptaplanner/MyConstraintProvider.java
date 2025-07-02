package com.cdruf.hellooptaplanner;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import static org.optaplanner.core.api.score.stream.Joiners.equal;

public class MyConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{valueConflict(constraintFactory), prohibit_7(constraintFactory)};
    }

    /**
     * Each value can be used at most once.
     */
    private Constraint valueConflict(ConstraintFactory factory) {
        return factory.forEachUniquePair(MyEntity.class, equal(MyEntity::getX)).penalize(HardSoftScore.ONE_HARD)
                      .asConstraint("Value conflict");
    }

    private Constraint prohibit_7(ConstraintFactory factory) {
        return factory.forEach(MyEntity.class)
                      .filter(var -> var.getX().getValue() == 7)
                      .penalize(HardSoftScore.ONE_HARD)
                      .asConstraint("No 7");

    }


}