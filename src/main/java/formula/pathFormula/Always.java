package formula.pathFormula;

import formula.FormulaParser;
import formula.Result;
import formula.stateFormula.*;
import model.Loop;
import model.Model;
import model.State;
import model.Transition;

import java.util.*;

public class Always extends PathFormula {
    public final StateFormula stateFormula;
    private Set<String> actions = new HashSet<String>();

    public Always(StateFormula stateFormula, Set<String> actions) {
        this.stateFormula = stateFormula;
        this.actions = actions;
    }

    public Set<String> getActions() {
        return actions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.ALWAYS_TOKEn);
        stateFormula.writeToBuffer(buffer);
    }

    @Override
    public Result checkFormula(Model model, State currentState) {
        return checkPath(model, currentState, new HashSet<String>()/*, new ArrayList<Transition>()*/);
    }

    private Result checkPath(Model model, State currentState, HashSet<String> visitedStates/*, List<Transition> trans*/) {
        visitedStates.add(currentState.getName());
        System.out.println("Always " + currentState.getName());

        Result result = stateFormula.checkFormula(model, currentState);

        if (!result.holds) {
            return new Result(false, false, result.trace);
        }


        //loop through all transitions from thi state and recur
        for (Transition t:model.getTransitions()) {
            //check if the current state is the source of transition
            if (t.getSource().equals(currentState.getName()) && !visitedStates.contains(t.getTarget())) {

                Result recurDown = checkPath(model, model.getStatesMap().get(t.getTarget()), visitedStates);

                if (!recurDown.holds) {
                    recurDown.trace.add(currentState.getName());
                    return new Result(false, false, recurDown.trace);
                }
            }
        }
        return new Result(true, false, null);
    }
}
