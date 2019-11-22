package model;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;

/**
 * A model is consist of states and transitions
 */
public class Model {
    State[] states;
    Transition[] transitions;
    private Map<String, State> statesMap = null;
    private List<Transition> transitionsList = null;

    public static Model parseModel(String filePath) throws IOException {
        Gson gson = new Gson();
        Model model = gson.fromJson(new FileReader(filePath), Model.class);
        for (Transition t : model.transitions) {
            System.out.println(t);
        }
        return model;
    }

    /**
     * Returns the list of transitions
     * 
     * @return list of transition for the given model
     */
    public List<Transition> getTransitions() {
        return transitionsList;
    }

    public Map<String, State> getStates() {
        return statesMap;
    }

    private void fillStatesMap() {
        statesMap = new HashMap<String, State>();
        for (State s: states) {
            statesMap.put(s.getName(), s);
        }
    }

    private void transitionsToList() {
        transitionsList = new ArrayList<Transition>();
        for (Transition t: transitions) {
            transitionsList.add(t);
        }
    }

    public void fillModel() {
        if (statesMap == null) {
            fillStatesMap();
            transitionsToList();
        }
    }

    public List<Transition> getTransitionsFromState(String stateName) {
        List<Transition> results = new ArrayList<Transition>();
        for (Transition t: transitionsList) {
            if (t.getSource().equals(stateName)) {
                results.add(t);
            }
        }
        return results;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(new ModelJson(statesMap.values().toArray(new State[0]), transitionsList.toArray(new Transition[0])));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("States:\n");
        statesMap.values().stream().map(x -> x.getName()).forEach(x -> sb.append(x + "\n"));

        sb.append("Transitions:\n");
        transitionsList.forEach(x -> sb.append(x + "\n"));

        return  sb.toString();
    }
}
class ModelJson {
    State[] states;
    Transition[] transitions;
    public ModelJson(State[] s, Transition[] ts) {
        states = s;
        transitions = ts;
    }
}