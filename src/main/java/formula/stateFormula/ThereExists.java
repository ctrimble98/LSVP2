package formula.stateFormula;

import formula.FormulaParser;
import formula.Result;
import formula.pathFormula.PathFormula;
import model.Model;
import model.State;
import model.Transition;

import java.util.ArrayList;
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
        List<Transition> path = null;
        for (Result result : paths) {
            if (result.holds) {
                return new Result(true, result.trace, result.path);
            } else {
                trace = result.trace;
                path = result.path;
            }
        }

        return new Result(false, trace, path);
    }
}
