package formula.stateFormula;

import formula.Result;
import model.Model;
import model.State;
import model.Transition;

import java.util.ArrayList;
import java.util.List;

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

        List<String> trace;
        if (value) {
            trace = new ArrayList<>();
        } else {
            trace = new ArrayList<String>();
            trace.add(currentState.getName());
        }

        return new Result(value, trace, new ArrayList<Transition>());
    }
}
