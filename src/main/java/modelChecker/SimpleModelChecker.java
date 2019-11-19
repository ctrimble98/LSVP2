package modelChecker;

import formula.stateFormula.*;
import formula.pathFormula.*;
import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class SimpleModelChecker implements ModelChecker {

    private HashMap<String, State> states = new HashMap<String, State>();
    private ArrayList<String> trace = new ArrayList<String>();
    private HashSet<Loop> loops = new HashSet<Loop>();

    private boolean checkStateFormula(Model model, StateFormula formula, State currentState) {
        if (formula instanceof And) {

            return checkStateFormula(model, ((And)formula).left, currentState) && checkStateFormula(model, ((And)formula).right, currentState);
        } else if (formula instanceof Or) {

            return checkStateFormula(model, ((Or)formula).left, currentState) || checkStateFormula(model, ((Or)formula).right, currentState);
        } else if (formula instanceof Not) {

            return !checkStateFormula(model, ((Not)formula).stateFormula, currentState);
        } else if (formula instanceof AtomicProp) {

            String label = ((AtomicProp)formula).label;
            String[] labels = currentState.getLabel();

            for (String l:labels) {
                if (l.equals(label)) {
                    return true;
                }
            }
            return false;
        } else if (formula instanceof BoolProp) {

            return ((BoolProp)formula).value;
        } else if (formula instanceof ThereExists) {
            //TODO there exists (not forall?). Could replace all with not forall

        } else if (formula instanceof ForAll) {
            //TODO unknown if works
            return checkPathFormula(model, ((ForAll)formula).pathFormula, currentState);
        }
        return false;
    }

    private boolean checkPathFormula(Model model, PathFormula formula, State currentState) {
        if (formula instanceof Always) {

        } else if (formula instanceof Eventually) {

        } else if (formula instanceof Next) {

        } else if (formula instanceof Until) {

        }
        return false;
    }

    private boolean checkState(Model model, StateFormula constraint, StateFormula query, State currentState, HashSet<String> visitedStates, ArrayList<Transition> trans) {
        visitedStates.add(currentState.getName());
        System.out.println(currentState.getName());
        //loop through all transitions from thi state and recur

        for (Transition t:model.getTransitions()) {
            //check if the current state is the source of transition
            if (t.getSource().equals(currentState.getName())) {

                //TODO check if constraint applies in this case
                boolean matches = true;

                //recurse down
                ArrayList<Transition> newTrans = new ArrayList<Transition>(trans);
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
