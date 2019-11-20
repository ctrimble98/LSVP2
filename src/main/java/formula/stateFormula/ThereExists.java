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
        //TODO Path result result
        Result r = new Result(false, true);
        if (satisfied) {
            r.holds = true;
        } else if (pathFormula.checkFormula(model, currentState)) {
            satisfied = true;
            r.holds = true;
            r.continueSearch = false;
        }
        return r;
    }
}
