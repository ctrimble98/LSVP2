package formula.pathFormula;

import formula.FormulaParser;
import formula.Result;
import formula.stateFormula.*;
import model.Model;
import model.State;
import model.Transition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    public Set<Result> checkFormula(Model model, State currentState) {
        Set<Result> results = new HashSet<>();

        for (Transition t:model.getTransitions()) {
            //check if the current state is the source of transition
            if (t.getSource().equals(currentState.getName())) {
                //potential next state

                Result result = stateFormula.checkFormula(model, model.getStates().get(t.getTarget()));

                boolean actionMatch = actionMatch(actions, t);

                if (!result.holds) {
                    //left res doesn't hold and neither does right res, fail
                    result.path.add(t);
                    results.add(new Result(false, result.trace, result.path));
                } else if (!actionMatch) {
                    //TODO better action trace
                    List<String> trace = new ArrayList<String>();
                    trace.add(currentState.getName());
                    results.add(new Result(false, trace, new ArrayList<Transition>()));
                } else {
                    //left res down't hold but right res does, success
                    results.add(new Result(true, null, null));
                }
            }
        }

        return results;
    }
}
