package formula.stateFormula;

import formula.*;
import formula.pathFormula.PathFormula;
import model.Model;
import model.State;

import java.util.List;
import java.util.Set;

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
        Set<Result> paths = pathFormula.checkFormula(model, currentState);


        boolean allMatch = true;

        List<String> trace = null;
        for (Result result:paths) {
            if (!result.holds) {
                allMatch = false;
                trace = result.trace;
                break;
            }
        }

        return new Result(allMatch, trace);
    }
}
