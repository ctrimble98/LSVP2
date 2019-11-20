package formula.stateFormula;

import formula.Result;
import model.Model;
import model.State;
import model.Transition;

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
        return new Result(leftResult.holds && rightResult.holds, leftResult.continueSearch || rightResult.continueSearch);
    }
}