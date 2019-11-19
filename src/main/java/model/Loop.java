package model;

import java.util.*;

public class Loop {

    private List<Transition> transitions;

    public Loop(ArrayList<Transition> transitions) {
        int i = 0;
        int last = transitions.size() - 1;
        while (!transitions.get(i).getSource().equals(transitions.get(last).getTarget())) {
            i++;
        }
        this.transitions = transitions.subList(i, transitions.size());
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    @Override
    public int hashCode() {

        int hc = 0;
        for (Transition t:transitions) {
            hc ^= t.hashCode();
        }
        return hc;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof  Loop) {
            return other.hashCode() == this.hashCode();
        }
        return false;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (Transition t: transitions) {
            sb.append(t.getSource() + " -> ");
        }
        sb.append(transitions.get(transitions.size()-1).getTarget());
        return sb.toString();
    }
}
