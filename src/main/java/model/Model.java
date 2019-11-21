package model;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.google.gson.Gson;

/**
 * A model is consist of states and transitions
 */
public class Model {
    State[] states;
    Transition[] transitions;
    private Map<String, State> statesMap = new HashMap<String, State>();
    private List<Transition> transitionsList = new ArrayList<Transition>();
    private Set<Loop> loops = new HashSet<Loop>();

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

    public Set<Loop> getLoops() {
        return loops;
    }

    public void fillStatesMap() {
        for (State s: states) {
            statesMap.put(s.getName(), s);
        }
    }

    public void transitionsToList() {
        for (Transition t: transitions) {
            transitionsList.add(t);
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
}
