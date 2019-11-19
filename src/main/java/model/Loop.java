package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class Loop {

    private ArrayList<Transition> transitions;

    public Loop(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public ArrayList<Transition> getTransitions() {
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
        String s = "";
        for (Transition t: transitions) {
            s += t.getSource() + " -> ";
        }
        s += transitions.get(transitions.size()-1).getTarget();
        return s;
    }
}
