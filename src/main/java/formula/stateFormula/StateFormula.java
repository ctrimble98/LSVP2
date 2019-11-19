package formula.stateFormula;

import model.*;

public abstract class StateFormula {
    public abstract void writeToBuffer(StringBuilder buffer);

    public abstract boolean checkFormula(Model model, State currentState);

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        writeToBuffer(buffer);
        return buffer.toString();
    }
}
