package model;

import java.util.HashSet;

/**
 * 
 * */
public class State {
    private boolean init;
    private String name;
    private String [] label;
    private HashSet<Loop> loops = new HashSet<Loop>();
	
    /**
     * Is state an initial state
     * @return boolean init 
     * */
    public boolean isInit() {
	return init;
    }
    public void setInit(boolean init) {
        this.init = init;
    }
	
    /**
     * Returns the name of the state
     * @return String name 
     * */
    public String getName() {
	return name;
    }
	
    /**
     * Returns the labels of the state
     * @return Array of string labels
     * */
    public String[] getLabel() {
	return label;
    }

    /**
     * Returns the loops the state is in
     * @return Set of loops
     */
    public HashSet<Loop> getLoops() {
        return loops;
    }

    public void addLoop(Loop loop) {
        loops.add(loop);
    }
}
