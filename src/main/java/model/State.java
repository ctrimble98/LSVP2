package model;

import java.util.HashSet;

/**
 * 
 * */
public class State {
    private boolean init;
    private String name;
    private String [] label;

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


}
