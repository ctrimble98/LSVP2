package formula.pathFormula;

import formula.PathResult;
import formula.Result;
import model.Loop;
import model.Model;
import model.State;
import model.Transition;

import java.util.HashSet;
import java.util.Set;

public abstract class PathFormula {

    public abstract void writeToBuffer(StringBuilder buffer);

    protected boolean actionMatch(Set<String> actions, Transition t) {
        //check that a transition action matches the left actions
        boolean actionMatch = false;
        for (String action:t.getActions()) {
            if (actions.contains(action)) {
                actionMatch = true;
                break;
            }
        }
        return actionMatch;
    }

    public abstract Set<PathResult> checkFormula(Model model, State currentState);
}
