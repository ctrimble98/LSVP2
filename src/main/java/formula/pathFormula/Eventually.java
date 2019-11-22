package formula.pathFormula;

import formula.FormulaParser;
import formula.Result;
import formula.stateFormula.*;
import model.Model;
import model.State;
import model.Transition;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class Eventually extends PathFormula {
    public final StateFormula stateFormula;
    private Set<String> leftActions;
    private Set<String> rightActions;

    public Eventually(StateFormula stateFormula, Set<String> leftActions, Set<String> rightActions) {
        super();
        this.stateFormula = stateFormula;
        this.leftActions = leftActions;
        this.rightActions = rightActions;
    }

    public Set<String> getLeftActions() {
        return leftActions;
    }

    public Set<String> getRightActions() {
        return rightActions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.EVENTUALLY_TOKEN);
        stateFormula.writeToBuffer(buffer);
    }

    @Override
    public Set<Result>  checkFormula(Model model, State currentState) {
        return checkPath(model, currentState, new HashSet<String>(), new ArrayList<Transition>());
    }

    private Set<Result> checkPath(Model model, State currentState, HashSet<String> visitedStates, List<Transition> transitions) {
        visitedStates.add(currentState.getName());

        Result stateResult = stateFormula.checkFormula(model, currentState);

        List<String> trace = !stateResult.holds ? stateResult.trace : new ArrayList<String>();
        List<Transition> path = !stateResult.holds ? stateResult.path : new ArrayList<Transition>();

        Set<Result> results = new HashSet<Result>();

        //TODO add actions

        boolean seenRight = rightActions.isEmpty() || (transitions.size() > 0 && actionMatch(rightActions, transitions.get(transitions.size() - 1)));


        boolean seenLeft = false;

        if (seenRight) {
            if (leftActions.isEmpty()) {
                seenLeft = true;
            } else {
                for (Transition t : transitions.subList(0, transitions.size() - 1)) {
                    if (actionMatch(leftActions, t)) {
                        seenLeft = true;
                        break;
                    }
                }
            }
        }

        //if the condition holds this is a positive result
        if (seenLeft && stateResult.holds) {
            results.add(new Result(true, null, null));
        } else {
            boolean lastState = true;

            //loop through all transitions from thi state and recur
            for (Transition t : model.getTransitionsFromState(currentState.getName())) {
                //this isn't the end of a chain of execution
                lastState = false;

                //check if we have been to this target before
                if (!visitedStates.contains(t.getTarget())) {

                    List<Transition> newTrans = new ArrayList<Transition>(transitions);
                    newTrans.add(t);

                    Set<Result> recurDown = checkPath(model, model.getStates().get(t.getTarget()), visitedStates, newTrans);

                    for (Result res : recurDown) {
                        if (!res.holds) {
                            res.trace.add(currentState.getName());
                            res.path.add(t);
                        }
                    }

                    results.addAll(recurDown);
                } else {
                    //if we've seen this case before we need to look ahead one since we may not have considered this transition
                    Result nextResult = stateFormula.checkFormula(model, model.getStates().get(t.getTarget()));

                    boolean nextSeenRight = rightActions.isEmpty() || actionMatch(rightActions, t);

                    boolean nextSeenLeft = false;

                    if (nextSeenRight) {
                        if (leftActions.isEmpty()) {
                            nextSeenLeft = true;
                        } else {
                            for (Transition t2 : transitions) {
                                if (actionMatch(leftActions, t2)) {
                                    nextSeenLeft = true;
                                    break;
                                }
                            }
                        }
                    }
                    //if the condition holds this is a positive result
                    if (nextSeenLeft && nextResult.holds) {
                        results.add(new Result(true, null, null));
                    } else {

                        //this target state has been visited before so we're at the end of a loop, and haven't met condition
                        results.add(new Result(false, trace, path));
                    }
                }
            }
            //reach end of execution without meeting the condition
            if (lastState) {
                results.add(new Result(false, trace, path));
            }
        }
        return results;
    }
}
