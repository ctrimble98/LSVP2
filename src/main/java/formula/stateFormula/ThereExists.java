package formula.stateFormula;

import formula.FormulaParser;
import formula.Result;
import formula.pathFormula.PathFormula;
import model.Model;
import model.State;

import java.util.List;
import java.util.Set;

public class ThereExists extends StateFormula {
    public final PathFormula pathFormula;

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
        Set<Result> paths = pathFormula.checkFormula(model, currentState);

        List<String> trace = null;
        for (Result result:paths) {
            if (result.holds) {
                return new Result(true, null);
            } else {
                trace = result.trace;
            }
        }

        return new Result(false, trace);
    }
}
