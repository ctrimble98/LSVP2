package formula.stateFormula;

import formula.Result;
import model.Model;
import model.State;

public class And extends StateFormula {
    public final StateFormula left;
    public final StateFormula right;

    public And(StateFormula left, StateFormula right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" && ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public Result checkFormula(Model model, State currentState) {
        Result leftResult = left.checkFormula(model, currentState);
        Result rightResult = right.checkFormula(model, currentState);

        if (!leftResult.holds) {
            //leftResult.trace.add(currentState.getName());
            return new Result(false, leftResult.trace, leftResult.path);
        } else if (!rightResult.holds) {
            //rightResult.trace.add(currentState.getName());
            return new Result(false, rightResult.trace, rightResult.path);
        } else {
            return new Result(true, null, null);
        }
    }
}