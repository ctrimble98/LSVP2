package formula.stateFormula;

import model.Model;
import model.State;

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
    public boolean checkFormula(Model model, State currentState) {
        String[] labels = currentState.getLabel();

        for (String l:labels) {
            if (l.equals(label)) {
                return true;
            }
        }
        return false;
    }

}
