package formula.stateFormula;

import model.Model;
import model.State;

public class BoolProp extends StateFormula {
    public final boolean value;

    public BoolProp(boolean value) {
        this.value = value;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        String stringValue = (value) ? "True" : "False";
        buffer.append(" " + stringValue + " ");
    }

    @Override
    public boolean checkFormula(Model model, State currentState) {
        return value;
    }
}
