package formula.pathFormula;

import formula.*;
import formula.stateFormula.*;
import model.Model;
import model.State;
import model.Transition;

import java.util.HashSet;
import java.util.Set;

public class Until extends PathFormula {
    public final StateFormula left;
    public final StateFormula right;
    private Set<String> leftActions;
    private Set<String> rightActions;

    public Until(StateFormula left, StateFormula right, Set<String> leftActions, Set<String> rightActions) {
        super();
        this.left = left;
        this.right = right;
        this.leftActions = leftActions;
        this.rightActions = rightActions;
    }

    public Set<String> getLeftActions() {
        return leftActions;
    }

    public Set<String> getRightActions() {
        return rightActions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" " + FormulaParser.UNTIL_TOKEN + " ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public Set<PathResult> checkFormula(Model model, State currentState) {
        return checkPath(model, currentState, new HashSet<String>()/*, new ArrayList<Transition>()*/);
    }

    private Set<PathResult> checkPath(Model model, State currentState, HashSet<String> visitedStates/*, List<Transition> trans*/) {
        visitedStates.add(currentState.getName());

        Set<PathResult> results = new HashSet<PathResult>();

        //loop through all transitions from thi state and recur
        for (Transition t:model.getTransitions()) {
            //check if the current state is the source of transition
            if (t.getSource().equals(currentState.getName()) && !visitedStates.contains(t.getTarget())) {

                Result leftRes = left.checkFormula(model, currentState);

                boolean leftActionMatch = actionMatch(leftActions, t);

                if (!leftRes.holds || !leftActionMatch) {

                    Result rightRes = left.checkFormula(model, currentState);

                    //check that a transition action matches the right actions
                    boolean rightActionMatch = actionMatch(rightActions, t);

                    if (!rightRes.holds || !rightActionMatch) {
                        //left res doesn't hold and neither does right res, fail
                        results.add(new PathResult(false, leftRes.trace));
                    } else {
                        //left res down't hold but right res does, success
                        results.add(new PathResult(true, null));
                    }
                } else {
                    //left res holds so continue
                    Set<PathResult> recurDown = checkPath(model, model.getStatesMap().get(t.getTarget()), visitedStates);

                    for (PathResult res:recurDown) {
                        if (res.trace != null) {
                            res.trace.add(currentState.getName());
                        }
                    }

                    results.addAll(recurDown);
                }
            }
        }

        //condition held return success
        return results;
    }
}
