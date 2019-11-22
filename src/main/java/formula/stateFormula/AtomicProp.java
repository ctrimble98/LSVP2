package formula.stateFormula;

import formula.Result;
import model.Model;
import model.State;
import model.Transition;

import java.util.ArrayList;
import java.util.List;

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
                ArrayList<String> trace = new ArrayList<String>();
                trace.add(currentState.getName());
                return new Result(true, trace, new ArrayList<Transition>());
            }
        }

        List<String> trace = new ArrayList<String>();
        //trace.add(currentState.getName());
        return new Result(false, trace, new ArrayList<Transition>());
    }

}
