package formula;

import java.util.List;
import java.util.Set;

public class PathResult {
    public boolean holds;

    public List<String> trace;

    public PathResult(boolean holds, List<String> trace) {
        this.holds = holds;
        this.trace = trace;
    }
}
