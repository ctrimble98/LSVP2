package modelChecker;

import formula.Result;
import formula.stateFormula.*;
import model.*;

import java.util.*;

public class SimpleModelChecker implements ModelChecker {

    private List<String> trace = new ArrayList<String>();

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        model.fillStatesMap();
        model.transitionsToList();

        Set<Transition> invalidTransitions = new HashSet<Transition>();

        for (Map.Entry<String, State> e:model.getStatesMap().entrySet()) {
            State s = e.getValue();
            if (s.isInit()) {


                Result res = constraint.checkFormula(model, s);
                while (!res.holds) {
                    if (res.path == null) {
                        System.out.println(trace.size());
                    }
                    if (res.path.size() > 0) {
                        model.getTransitions().remove(res.path.get(res.path.size() - 1));
                    } else {
                        model.getTransitions().removeIf(x -> x.getSource().equals(s.getName()) || x.getTarget().equals(s.getName()));
                    }
                    res = constraint.checkFormula(model, s);
                }
            }
        }




        for (Map.Entry<String, State> e:model.getStatesMap().entrySet()) {
            State s = e.getValue();
            if (s.isInit()) {

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

        for (Loop l: model.getLoops()) {
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
