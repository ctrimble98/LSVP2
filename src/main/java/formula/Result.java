package formula;

import java.util.List;

public class Result {
    public boolean holds;
    public boolean continueSearch;
    public List<String> trace;

    public Result(boolean holds, boolean continueSearch, List<String> trace) {
        this.holds = holds;
        this.continueSearch = continueSearch;
        this.trace = trace;
    }
}
