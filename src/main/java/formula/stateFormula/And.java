package formula.stateFormula;

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
    public boolean checkFormula(Model model, State currentState) {
        return left.checkFormula(model, currentState) && right.checkFormula(model, currentState);
    }
}