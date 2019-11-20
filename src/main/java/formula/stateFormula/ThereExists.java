package formula.stateFormula;

import formula.FormulaParser;
import formula.Result;
import formula.pathFormula.PathFormula;
import model.Model;
import model.State;
import model.Transition;

public class ThereExists extends StateFormula {
    public final PathFormula pathFormula;
    private boolean satisfied = false;

    public ThereExists(PathFormula pathFormula) {
        this.pathFormula = pathFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        buffer.append(FormulaParser.THEREEXISTS_TOKEN);
        pathFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public Result checkFormula(Model model, State currentState) {
        Result r = pathFormula.checkFormula(model, currentState);

        if (satisfied) {
            r.holds = true;
            r.trace = null;
        } else if (r.holds) {
            satisfied = true;
            r.holds = true;
            r.continueSearch = false;
            r.trace = null;
        }
        return r;
    }
}
