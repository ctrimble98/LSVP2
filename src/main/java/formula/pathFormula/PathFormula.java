package formula.pathFormula;

import formula.Result;
import model.Loop;
import model.Model;
import model.State;
import model.Transition;

import java.util.HashSet;
import java.util.Set;

public abstract class PathFormula {

    public abstract void writeToBuffer(StringBuilder buffer);

    public abstract Result checkFormula(Model model, State currentState, Transition currentTransition);
}
