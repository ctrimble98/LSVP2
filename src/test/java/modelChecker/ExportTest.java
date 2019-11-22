package modelChecker;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ExportTest {

    @Test
    public void exportModelTest() {
        try {
            Model model = Model.parseModel("src/test/resources/mutex/model.json");

            StateFormula mutex = new FormulaParser("src/test/resources/mutex/mutex.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/mutex/mutex.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            mc.constrainModel(model, mutex);

            try (FileWriter file = new FileWriter("src/test/resources/exportedmodels/exportmutex.json")) {
                file.write(model.toJson());
            }

            Model newModel = Model.parseModel("src/test/resources/exportedmodels/exportmutex.json");

            assertTrue(mc.check(newModel, mutex, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}
