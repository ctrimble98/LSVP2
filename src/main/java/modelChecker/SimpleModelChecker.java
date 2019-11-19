package modelChecker;

import formula.stateFormula.StateFormula;
import model.Model;
import model.State;
import model.Transition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class SimpleModelChecker implements ModelChecker {

    private ArrayList<String> trace = new ArrayList<String>();


    private HashMap<String, State> states = new HashMap<String, State>();

    private boolean checkState(Model model, StateFormula constraint, StateFormula query, State currentState, HashSet<String> visitedStates) {
        visitedStates.add(currentState.getName());
        System.out.println(currentState.getName());
        //loop through all transitions from thi state and recur

        for (Transition t:model.getTransitions()) {
            //check if the current state is the source of transition
            if (t.getSource().equals(currentState.getName())) {

                //TODO check if constraint applies in this case
                boolean matches = true;

                //recurse down
                //System.out.println(matches);

                if (matches && !visitedStates.contains(t.getTarget())) {
                    matches = checkState(model, constraint, query, states.get(t.getTarget()), new HashSet<String>(visitedStates));
                }

                if (!matches) {
                    //TODO build up trace using transition and state
                    trace.add(currentState.getName());
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        for (State s:model.getStates()) {
            states.put(s.getName(), s);
        }


        for (State s:model.getStates()) {
            if (s.isInit()) {
                HashSet<String> visitedStates = new HashSet<String>();


                if (!checkState(model, constraint, query, s, visitedStates)) {
                    System.out.println("Trace");
                    for (String st: getTrace()) {
                        System.out.println(st);
                    }
                    return false;
                }

                System.out.println();
            }
        }

        return true;
    }

    @Override
    public String[] getTrace() {
        // TODO Auto-generated method stub
        Collections.reverse(trace);
        return trace.toArray(new String[trace.size()]);
    }

}
