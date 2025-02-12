package formula.stateFormula;

import formula.*;
import formula.pathFormula.PathFormula;
import model.Model;
import model.State;

import java.util.ArrayList;
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

        Result r = paths.size() > 0 ? paths.iterator().next() : new Result(true, new ArrayList<>(), new ArrayList<>());
        for (Result result:paths) {
            if (!result.holds) {
                return new Result(false, result.trace, result.path);
            } else {
                r = result;
            }
        }

        return r;
    }
}
