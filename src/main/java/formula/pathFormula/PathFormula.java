package formula.pathFormula;

import model.Model;
import model.State;

public abstract class PathFormula {
    public abstract void writeToBuffer(StringBuilder buffer);

    public abstract boolean checkFormula(Model model, State currentState);
}
