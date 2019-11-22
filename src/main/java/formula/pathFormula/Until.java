package formula.pathFormula;

import formula.*;
import formula.stateFormula.*;
import model.Model;
import model.State;
import model.Transition;

import java.util.*;

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
        for (Transition t:model.getTransitionsFromState(currentState.getName())) {
            //check if the current state is the source of transition
            if (!visitedStates.contains(t.getTarget())) {

                Result rightRes = right.checkFormula(model, model.getStates().get(t.getTarget()));

                boolean rightActionMatch = actionMatch(rightActions, t);

                if (!rightRes.holds || !rightActionMatch) {

                    Result leftRes = left.checkFormula(model, model.getStates().get(t.getTarget()));

                    //check that a transition action matches the right actions
                    boolean leftActionMatch = actionMatch(leftActions, t);

                    if (!leftRes.holds) {
                        //left res doesn't hold and neither does right res, fail
                        leftRes.trace.add(currentState.getName());
                        leftRes.path.add(t);
                        results.add(new Result(false, leftRes.trace, leftRes.path));
                    } else if (!leftActionMatch) {
                        //TODO better action trace
                        List<String> trace = new ArrayList<String>();
                        trace.add(currentState.getName());
                        ArrayList<Transition> path = new ArrayList<Transition>();
                        path.add(t);
                        results.add(new Result(false, trace, path));
                    } else {
                        //right don't hold but left do, keep looking
                        if (!visitedStates.contains(t.getTarget())) {
                            //left res holds so continue
                            Set<Result> recurDown = checkPath(model, model.getStates().get(t.getTarget()), visitedStates);

                            for (Result res : recurDown) {
                                if (!res.holds) {
                                    res.trace.add(currentState.getName());
                                    res.path.add(t);
                                }
                            }

                            results.addAll(recurDown);
                        }
                    }
                } else {
                    //right conditions hold, this is success
                    results.add(new Result(true, null, null));
                }
            }
        }

        //condition held return success
        return results;
    }
}
