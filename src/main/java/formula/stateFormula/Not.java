package formula.stateFormula;

import formula.FormulaParser;
import model.Model;
import model.State;

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
    public boolean checkFormula(Model model, State currentState) {
        return !stateFormula.checkFormula(model, currentState);
    }
}
