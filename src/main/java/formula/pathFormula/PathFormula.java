package formula.pathFormula;

import formula.Result;
import model.Model;
import model.State;
import model.Transition;

import java.util.Set;

public abstract class PathFormula {

    public abstract void writeToBuffer(StringBuilder buffer);

    protected boolean actionMatch(Set<String> actions, Transition t) {
        //check that a transition action matches the left actions
        boolean actionMatch = false;

        //TODO should be if actions = null, empty set and not specified should be treated differently
        if (actions.size() == 0) { return true; }

        for (String action:t.getActions()) {
            if (actions.contains(action)) {
                actionMatch = true;
                break;
            }
        }
        return actionMatch;
    }

    public abstract Set<Result> checkFormula(Model model, State currentState);
}
