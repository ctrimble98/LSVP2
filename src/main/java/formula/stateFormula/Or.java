package formula.stateFormula;

import formula.Result;
import model.Model;
import model.State;

import java.util.ArrayList;
import java.util.List;

public class Or extends StateFormula {
    public final StateFormula left;
    public final StateFormula right;

    public Or(StateFormula left, StateFormula right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" || ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public Result checkFormula(Model model, State currentState) {
        Result leftResult = left.checkFormula(model, currentState);
        Result rightResult = right.checkFormula(model, currentState);

        List<String> trace;

        if (leftResult.holds || rightResult.holds) {
            trace = null;
        } else {
            //TODO somehow combine both traces?
            trace = new ArrayList<String>();
        }

        return new Result(leftResult.holds || rightResult.holds, trace);
    }
}