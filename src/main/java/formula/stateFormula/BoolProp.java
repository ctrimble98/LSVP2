package formula.stateFormula;

import formula.Result;
import model.Model;
import model.State;
import model.Transition;

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
    public Result checkFormula(Model model, State currentState) {

        return new Result(value, false);
    }
}
