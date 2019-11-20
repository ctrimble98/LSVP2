package formula.pathFormula;

import formula.FormulaParser;
import formula.PathResult;
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
    public Set<PathResult> checkFormula(Model model, State currentState) {
        return checkPath(model, currentState, new HashSet<String>()/*, new ArrayList<Transition>()*/);
    }

    private Set<PathResult> checkPath(Model model, State currentState, HashSet<String> visitedStates/*, List<Transition> trans*/) {
        visitedStates.add(currentState.getName());

        Result stateResult = stateFormula.checkFormula(model, currentState);

        Set<PathResult> results = new HashSet<PathResult>();

        if (stateResult.holds) {
            boolean lastState = true;

            //loop through all transitions from thi state and recur
            for (Transition t:model.getTransitions()) {
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
                        //this target state has been visited before so we're at the end of a loop
                        results.add(new PathResult(true, null));
                    }
                }
            }

            //this is the legal end of an execution so add it
            if (lastState) {
                results.add(new PathResult(true, null));
            }
        } else {
            results.add(new PathResult(false, stateResult.trace));
        }

        return results;
    }
}
