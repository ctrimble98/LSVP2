package formula.stateFormula;

import formula.*;
import formula.pathFormula.PathFormula;
import model.Model;
import model.State;
import model.Transition;

public class ForAll extends StateFormula {
    public final PathFormula pathFormula;

    public ForAll(PathFormula pathFormula) {
        this.pathFormula = pathFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        buffer.append(FormulaParser.FORALL_TOKEN);
        pathFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public Result checkFormula(Model model, State currentState) {
        //TODO is this right?
        Result result = pathFormula.checkFormula(model, currentState);
        return result;
    }
}
