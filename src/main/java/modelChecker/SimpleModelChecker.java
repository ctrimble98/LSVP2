package modelChecker;

import formula.stateFormula.*;
import formula.pathFormula.*;
import model.*;

import java.util.*;

public class SimpleModelChecker implements ModelChecker {

    private List<String> trace = new ArrayList<String>();

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        model.fillStatesMap();
        System.out.println(constraint instanceof ThereExists);


        for (State s:model.getStates()) {
            if (s.isInit()) {
                HashSet<String> visitedStates = new HashSet<String>();

                if (!query.checkFormula(model, s) {
                    System.out.println("Trace");
                    for (String st: getTrace()) {
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
