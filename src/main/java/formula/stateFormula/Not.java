package formula.stateFormula;

import formula.FormulaParser;
import formula.Result;
import model.Model;
import model.State;
import model.Transition;

import java.util.ArrayList;

public class Not extends StateFormula {
    public final StateFormula stateFormula;

    public Not(StateFormula stateFormula) {
        this.stateFormula = stateFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.NOT_TOKEN);
        buffer.append("(");
        stateFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public Result checkFormula(Model model, State currentState) {
        Result childResult = stateFormula.checkFormula(model, currentState);

        if (childResult.holds) {
            return new Result(false, childResult.trace, childResult.path);
        } else {
            return new Result(true, childResult.trace, childResult.path);
        }
    }
}
