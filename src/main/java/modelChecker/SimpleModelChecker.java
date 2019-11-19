package modelChecker;

import formula.stateFormula.*;
import formula.pathFormula.*;
import model.*;

import java.util.*;

public class SimpleModelChecker implements ModelChecker {

    private Map<String, State> states = new HashMap<String, State>();
    private List<String> trace = new ArrayList<String>();
    private Set<Loop> loops = new HashSet<Loop>();

    private boolean checkState(Model model, StateFormula constraint, StateFormula query, State currentState, HashSet<String> visitedStates, List<Transition> trans) {
        visitedStates.add(currentState.getName());
        System.out.println(currentState.getName());
        //loop through all transitions from thi state and recur

        for (Transition t:model.getTransitions()) {
            //check if the current state is the source of transition
            if (t.getSource().equals(currentState.getName())) {

                //TODO check if constraint applies in this case
                boolean matches = true;

                //recurse down
                List<Transition> newTrans = new ArrayList<Transition>(trans);
                newTrans.add(t);

                if (matches) {
                    if (!visitedStates.contains(t.getTarget())) {
                        matches = checkState(model, constraint, query, states.get(t.getTarget()), new HashSet<String>(visitedStates), newTrans);
                    } else {
                        loops.add(new Loop(newTrans));
                    }
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
        System.out.println(constraint instanceof ThereExists);


        for (State s:model.getStates()) {
            if (s.isInit()) {
                HashSet<String> visitedStates = new HashSet<String>();


                if (!checkState(model, constraint, query, s, visitedStates, new ArrayList<Transition>())) {
                    System.out.println("Trace");
                    for (String st: getTrace()) {
                        System.out.println(st);
                    }
                    return false;
                }

                System.out.println();
            }
        }

        for (Loop l: loops) {
            System.out.println(l);
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
