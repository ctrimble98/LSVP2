package formula.stateFormula;

import formula.*;
import formula.pathFormula.PathFormula;
import model.Model;
import model.State;

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
    public boolean checkFormula(Model model, State currentState) {
        return pathFormula.checkFormula(model, currentState);
    }
}
