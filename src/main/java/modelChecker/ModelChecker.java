package modelChecker;

import formula.stateFormula.StateFormula;
import model.Model;
import model.State;
import model.Transition;

import java.util.HashSet;
import java.util.List;

/**
 * Defines the interface to model checker.
 *
 */
public interface ModelChecker {
    /**
     * verifies whether the model satisfies the query under the given
     * constraint.
     * 
     * @param model
     *            - model to verify
     * @param constraint
     *            - the constraint applied to the model before verification
     *            against the query.
     * @param query
     *            - the state formula to verify the model against.
     * @return - true if the model satisfies the query under the applied
     *         constraint.
     */
    public boolean check(Model model, StateFormula constraint, StateFormula query);

    public void constrainModel(Model model, StateFormula constraint);

    public boolean checkQuery(Model model, StateFormula query);

    // Returns a trace of the previous check attempt if it failed.
    public String[] getTrace();
}
