package formula;

import model.Transition;

import java.util.List;
import java.util.Set;

public class Result {
    public boolean holds;

    public List<String> trace;
    public List<Transition> path;

    public Result(boolean holds, List<String> trace, List<Transition> path) {
        this.holds = holds;
        this.trace = trace;
        this.path = path;

        if (!holds && (trace == null || path == null)) {
            throw new IllegalArgumentException("Must provide path and trace");
        }
    }
}
