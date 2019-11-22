package modelChecker;

import formula.Result;
import formula.stateFormula.*;
import model.*;

import java.util.*;

public class SimpleModelChecker implements ModelChecker {

    private List<String> trace = new ArrayList<String>();

    @Override
    public void constrainModel(Model model, StateFormula constraint) {
        model.fillModel();

        System.out.println("Original model:\n" + model);

        System.out.println("Constraint: " + constraint);

        Iterator<Map.Entry<String, State>> iter = model.getStates().entrySet().iterator();
        while (iter.hasNext()) {
            State s = iter.next().getValue();
            if (s.isInit()) {

                Result res = constraint.checkFormula(model, s);
                while (!res.holds) {
                    if (res.path.size() > 0) {
                        model.getTransitions().remove(res.path.get(0));
                    } else {
                        model.getTransitions().removeIf(x -> x.getSource().equals(s.getName()) || x.getTarget().equals(s.getName()));
                    }
                    res = constraint.checkFormula(model, s);

                    //Set state to not an initial state if all paths dont meet the constraint
                    if (model.getTransitionsFromState(s.getName()).size() == 0) {
                        s.setInit(false);
                        break;
                    }
                }
            }
        }

        //remove unnecessary tests
        boolean deadTests;
        do {
            deadTests = false;

            Iterator<String> stateIter = model.getStates().keySet().iterator();
            while (stateIter.hasNext()) {
                String s = stateIter.next();
                boolean hasTransition = false;
                for (Transition t:model.getTransitions()) {
                    if (t.getSource().equals(s) || t.getTarget().equals(s)) {
                        hasTransition = true;
                    }
                }

                if (!hasTransition) {
                    deadTests = true;
                }

                //if not inital and no transitions, remove
                if (!hasTransition && !model.getStates().get(s).isInit()) {
                    stateIter.remove();
                }
            }



        } while (deadTests);


        System.out.println("Constrained model:\n" + model);
    }

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {

        constrainModel(model, constraint);
        return checkQuery(model, query);
    }

    @Override
    public boolean checkQuery(Model model, StateFormula query) {
        model.fillModel();

        System.out.println("Query: " + query);
        System.out.println();

        boolean haveTested = false;

        for (Map.Entry<String, State> e:model.getStates().entrySet()) {
            State s = e.getValue();
            if (s.isInit()) {
                haveTested = true;

                Result res = query.checkFormula(model, s);

                trace = res.trace;

                if (!res.holds) {
                    System.out.println("Trace");
                    for (String st: res.trace) {
                        System.out.println(st);
                    }
                    return false;
                }

                System.out.println();
            }
        }

        //only return true if we actually tested a state
        return haveTested;
    }

    @Override
    public String[] getTrace() {
        Collections.reverse(trace);
        return trace.toArray(new String[trace.size()]);
    }

}
