package com.cdruf.cloudbalancing;

import com.cdruf.nqueens.Queen;
import org.optaplanner.core.api.score.buildin.simple.SimpleScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;

import static org.optaplanner.core.api.score.stream.Joiners.equal;

public class ConstraintProvider implements org.optaplanner.core.api.score.stream.ConstraintProvider {

    /**
     * No constraints for vertical because each queen gets one column initially.
     */
    @Override
    public Constraint[] defineConstraints(ConstraintFactory factory) {
        return new Constraint[]{horizontalConflict(factory), ascendingDiagonalConflict(factory),
                descendingDiagonalConflict(factory)};
    }


    protected Constraint horizontalConflict(ConstraintFactory factory) {
        return factory.forEachUniquePair(Queen.class, equal(queen -> queen.getRow().getIndex()))
                      .penalize(SimpleScore.ONE).asConstraint("Horizontal conflict");
    }

    protected Constraint ascendingDiagonalConflict(ConstraintFactory factory) {
        return factory.forEachUniquePair(Queen.class, equal(Queen::getAscendingDiagonalIndex)).penalize(SimpleScore.ONE)
                      .asConstraint("Ascending diagonal conflict");
    }

    protected Constraint descendingDiagonalConflict(ConstraintFactory factory) {
        return factory.forEachUniquePair(Queen.class, equal(Queen::getDescendingDiagonalIndex))
                      .penalize(SimpleScore.ONE).asConstraint("Descending diagonal conflict");
    }

}
