package formula.pathFormula;

import formula.FormulaParser;
import formula.Result;
import formula.stateFormula.*;
import model.Model;
import model.State;
import model.Transition;
import modelChecker.ModelChecker;
import modelChecker.SimpleModelChecker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Next extends PathFormula {
    public final StateFormula stateFormula;
    private Set<String> actions;

    public Next(StateFormula stateFormula, Set<String> actions) {
        this.stateFormula = stateFormula;
        this.actions = actions;
    }

    public Set<String> getActions() {
        return actions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.NEXT_TOKEN);
        stateFormula.writeToBuffer(buffer);
    }

    @Override
    public Result checkFormula(Model model, State currentState) {

//        State nextState = model.getStatesMap().get(currentTransition.getTarget());
//        ModelChecker mc = new SimpleModelChecker();
//
//        return new Result(mc.checkState(model, stateFormula, null, nextState, new HashSet<String>(), new ArrayList<Transition>()), false);
        for (Transition t:model.getTransitions()) {
            //check if the current state is the source of transition
            if (t.getSource().equals(currentState.getName())) {
                //potential next state

                Result result = stateFormula.checkFormula(model, model.getStatesMap().get(t.getTarget()));

                if (!result.holds) {
                    result.trace.add(currentState.getName());
                    return result;
                }
            }
        }

        return new Result(true, false, null);
    }
}
