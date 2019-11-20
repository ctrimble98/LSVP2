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
        System.out.println(currentState.getName());
        //loop through all transitions from thi state and recur
        for (Transition t:model.getTransitions()) {
            //check if the current state is the source of transition
            if (t.getSource().equals(currentState.getName()) && !visitedStates.contains(t.getTarget())) {

                Result result = stateFormula.checkFormula(model, currentState);

                if (!result.holds) {
                    result.trace.add(currentState.getName());
                    return new Result(false, false, result.trace);
                }

                Result recurDown = checkPath(model, currentState, visitedStates);

                if (!recurDown.holds) {
                    result.trace.add(currentState.getName());
                    return new Result(false, false, result.trace);
                }

//                //recurse down
//                List<Transition> newTrans = new ArrayList<Transition>(trans);
//                newTrans.add(t);
//
//                if (visitedStates.contains(t.getTarget())) {
//                    model.getLoops().add(new Loop(newTrans));
//                }
            }
        }
        return new Result(true, false, null);
    }
}
