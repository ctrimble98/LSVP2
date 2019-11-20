package formula.pathFormula;

import formula.FormulaParser;
import formula.PathResult;
import formula.Result;
import formula.stateFormula.*;
import model.Model;
import model.State;
import model.Transition;

import java.util.*;

public class Eventually extends PathFormula {
    public final StateFormula stateFormula;
    private Set<String> leftActions;
    private Set<String> rightActions;

    public Eventually(StateFormula stateFormula, Set<String> leftActions, Set<String> rightActions) {
        super();
        this.stateFormula = stateFormula;
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
        buffer.append(FormulaParser.EVENTUALLY_TOKEN);
        stateFormula.writeToBuffer(buffer);
    }

    @Override
    public Set<PathResult>  checkFormula(Model model, State currentState) {
        return checkPath(model, currentState, new HashSet<String>()/*, new ArrayList<Transition>()*/);
    }

    private Set<PathResult>  checkPath(Model model, State currentState, HashSet<String> visitedStates/*, List<Transition> trans*/) {
        visitedStates.add(currentState.getName());

        Result stateResult = stateFormula.checkFormula(model, currentState);

        Set<PathResult> results = new HashSet<PathResult>();

        //TODO add actions

        //if the condition holds this is a positive result
        if (stateResult.holds) {
            results.add(new PathResult(true, null));
        } else {
            boolean lastState = true;

            //loop through all transitions from thi state and recur
            for (Transition t : model.getTransitions()) {
                //check if the current state is the source of transition
                if (t.getSource().equals(currentState.getName())) {
                    //this isn't the end of a chain of execution
                    lastState = false;

                    //check if we have been to this target before
                    if (!visitedStates.contains(t.getTarget())) {
                        Set<PathResult> recurDown = checkPath(model, model.getStatesMap().get(t.getTarget()), visitedStates);

                        for (PathResult res:recurDown) {
                            if (res.trace != null) {
                                res.trace.add(currentState.getName());
                            }
                        }

                        results.addAll(recurDown);
                    } else {
                        //this target state has been visited before so we're at the end of a loop, and haven't met condition
                        results.add(new PathResult(false, stateResult.trace));
                    }
                }
            }

            //reach end of execution without meeting the condition
            if (lastState) {
                results.add(new PathResult(false, stateResult.trace));
            }
        }
        return results;
    }
}
