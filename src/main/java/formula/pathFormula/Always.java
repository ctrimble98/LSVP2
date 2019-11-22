package formula.pathFormula;

import formula.FormulaParser;
import formula.Result;
import formula.stateFormula.*;
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
    public Set<Result> checkFormula(Model model, State currentState) {
        return checkPath(model, currentState, new HashSet<String>());
    }

    private Set<Result> checkPath(Model model, State currentState, HashSet<String> visitedStates) {
        visitedStates.add(currentState.getName());

        Result stateResult = stateFormula.checkFormula(model, currentState);

        Set<Result> results = new HashSet<Result>();

        if (stateResult.holds) {
            boolean lastState = true;

            //loop through all transitions from thi state and recur
            for (Transition t:model.getTransitionsFromState(currentState.getName())) {
                //this isn't the end of a chain of execution
                lastState = false;

                boolean actionMatch = actionMatch(actions, t);

                if (!actionMatch) {
                    List<String> trace = new ArrayList<String>();
                    trace.add(currentState.getName());
                    List<Transition> path = new ArrayList<Transition>();
                    path.add(t);
                    results.add(new Result(false, trace, path));
                } else {
                    //check if we have been to this target before
                    if (!visitedStates.contains(t.getTarget())) {
                        Set<Result> recurDown = checkPath(model, model.getStates().get(t.getTarget()), visitedStates);

                        for (Result res:recurDown) {
                            res.trace.add(currentState.getName());
                            res.path.add(t);
                        }

                        results.addAll(recurDown);
                    } else {
                        List<String> trace = new ArrayList<String>();
                        trace.add(currentState.getName());
                        List<Transition> path = new ArrayList<Transition>();
                        path.add(t);
                        //this target state has been visited before so we're at the end of a loop
                        results.add(new Result(true, trace, path));
                    }
                }
            }

            //this is the legal end of an execution so add it
            if (lastState) {
                results.add(new Result(true, stateResult.trace, stateResult.path));
            }
        } else {
            results.add(new Result(false, stateResult.trace, new ArrayList<Transition>()));
        }

        return results;
    }
}
