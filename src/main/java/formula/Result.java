package formula;

import java.util.List;
import java.util.Set;

public class Result {
    public boolean holds;

    public List<String> trace;

    public Result(boolean holds, List<String> trace) {
        this.holds = holds;
        this.trace = trace;
    }
}
