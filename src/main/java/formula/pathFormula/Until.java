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
    public Result checkFormula(Model model, State currentState) {
        return checkPath(model, currentState, new HashSet<String>()/*, new ArrayList<Transition>()*/);
    }

    private Result checkPath(Model model, State currentState, HashSet<String> visitedStates/*, List<Transition> trans*/) {
        visitedStates.add(currentState.getName());
        System.out.println("Until " + currentState.getName());
        //loop through all transitions from thi state and recur
        for (Transition t:model.getTransitions()) {
            //check if the current state is the source of transition
            if (t.getSource().equals(currentState.getName()) && !visitedStates.contains(t.getTarget())) {

                Result leftRes = left.checkFormula(model, currentState);

                //check that a transition action matches the left actions
                boolean leftActionMatch = false;
                for (String action:t.getActions()) {
                    if (leftActions.contains(action)) {
                        leftActionMatch = true;
                        break;
                    }
                }

                if (!leftRes.holds || !leftActionMatch) {

                    Result rightRes = left.checkFormula(model, currentState);

                    //check that a transition action matches the right actions
                    boolean rightActionMatch = false;
                    for (String action:t.getActions()) {
                        if (rightActions.contains(action)) {
                            rightActionMatch = true;
                            break;
                        }
                    }

                    if (!rightRes.holds || !rightActionMatch) {
                        //left res doesn't hold and neither does right res, fail
                        return new Result(false, false, leftRes.trace);
                    }

                    //left res down't hold but right res does, success
                    //but need to check the other transitions
                } else {
                    //left res holds so continue
                    Result recurDown = checkPath(model, model.getStatesMap().get(t.getTarget()), visitedStates);

                    //fail if later test fails
                    if (!recurDown.holds) {
                        recurDown.trace.add(currentState.getName());
                        return new Result(false, false, recurDown.trace);
                    }
                }
            }
        }

        //condition held return success
        return new Result(true, false, null);
    }
}
