package formula.pathFormula;

import formula.FormulaParser;
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
    public Result checkFormula(Model model, State currentState) {
        return checkPath(model, currentState, new HashSet<String>()/*, new ArrayList<Transition>()*/);
    }

    private Result checkPath(Model model, State currentState, HashSet<String> visitedStates/*, List<Transition> trans*/) {
        visitedStates.add(currentState.getName());
        System.out.println(currentState.getName());

        Result fail = null;

        //loop through all transitions from thi state and recur
        for (Transition t:model.getTransitions()) {
            //check if the current state is the source of transition
            if (t.getSource().equals(currentState.getName()) && !visitedStates.contains(t.getTarget())) {

                Result result = stateFormula.checkFormula(model, currentState);

                if (!result.holds) {
                    result.trace.add(currentState.getName());
                    fail = new Result(false, false, result.trace);
                } else {

                    Result recurDown = checkPath(model, currentState, visitedStates);

                    if (!recurDown.holds) {
                        recurDown.trace.add(currentState.getName());
                        fail = new Result(false, false, recurDown.trace);
                    } else {
                        //this condition holds
                        return new Result(true, false, null);
                    }
                }
            }
        }

        //create a trace from this point if there isn't one to extend
        if (fail == null) {
            List<String> trace = new ArrayList<>();
            trace.add(currentState.getName());
            fail = new Result(false, false, trace);
        }
        return fail;
    }
}
