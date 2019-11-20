package formula.stateFormula;

import formula.Result;
import model.*;

public abstract class StateFormula {
    public abstract void writeToBuffer(StringBuilder buffer);

    public abstract Result checkFormula(Model model, State currentState);

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        writeToBuffer(buffer);
        return buffer.toString();
    }
}
