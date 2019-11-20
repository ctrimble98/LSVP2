package model;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

/**
 * A model is consist of states and transitions
 */
public class Model {
    State[] states;
    Transition[] transitions;
    private Map<String, State> statesMap = new HashMap<String, State>();
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
     * Returns the list of the states
     * 
     * @return list of state for the given model
     */
    public State[] getStates() {
        return states;
    }

    /**
     * Returns the list of transitions
     * 
     * @return list of transition for the given model
     */
    public Transition[] getTransitions() {
        return transitions;
    }

    public Map<String, State> getStatesMap() {
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
}
