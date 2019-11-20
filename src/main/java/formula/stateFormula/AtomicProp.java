package formula.stateFormula;

import formula.Result;
import model.Model;
import model.State;
import model.Transition;

public class AtomicProp extends StateFormula {
    public final String label;

    public AtomicProp(String label) {
        this.label = label;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(" " + label + " ");
    }

    @Override
    public Result checkFormula(Model model, State currentState) {
        String[] labels = currentState.getLabel();

        for (String l:labels) {
            if (l.equals(label)) {
                return new Result(true, false);
            }
        }
        return new Result(false, false);
    }

}
