package formula.pathFormula;

import formula.*;
import formula.stateFormula.*;
import model.Model;
import model.State;
import model.Transition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    public Set<Result> checkFormula(Model model, State currentState) {
        return checkPath(model, currentState, new HashSet<String>()/*, new ArrayList<Transition>()*/);
    }

    private Set<Result> checkPath(Model model, State currentState, HashSet<String> visitedStates/*, List<Transition> trans*/) {
        visitedStates.add(currentState.getName());

        Set<Result> results = new HashSet<Result>();

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

                    if (!rightRes.holds) {
                        //left res doesn't hold and neither does right res, fail
                        leftRes.path.add(t);
                        results.add(new Result(false, leftRes.trace, leftRes.path));
                    } else if (!rightActionMatch) {
                        //TODO better action trace
                        List<String> trace = new ArrayList<String>();
                        trace.add(currentState.getName());
                        results.add(new Result(false, trace, new ArrayList<Transition>()));
                    } else {
                        //left res down't hold but right res does, success
                        results.add(new Result(true, null, null));
                    }
                } else {
                    if (!visitedStates.contains(t.getTarget())) {
                        //left res holds so continue
                        Set<Result> recurDown = checkPath(model, model.getStatesMap().get(t.getTarget()), visitedStates);

                        for (Result res : recurDown) {
                            if (res.trace != null) {
                                res.trace.add(currentState.getName());
                                res.path.add(t);
                            }
                        }

                        results.addAll(recurDown);
                    }
                }
            }
        }

        //condition held return success
        return results;
    }
}
