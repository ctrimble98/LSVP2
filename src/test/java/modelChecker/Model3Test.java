package modelChecker;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Model3Test {

    @Test
    public void untilTest() {
        try {
            Model model = Model.parseModel("src/test/resources/model3/model.json");

            StateFormula existsgoal = new FormulaParser("src/test/resources/model3/existsgoal.json").parse();
            StateFormula until = new FormulaParser("src/test/resources/model3/untiltest.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, until, existsgoal));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void nextTest() {
        try {
            Model model = Model.parseModel("src/test/resources/model3/model.json");

            StateFormula existsgoal = new FormulaParser("src/test/resources/model3/existsgoal.json").parse();
            StateFormula next = new FormulaParser("src/test/resources/model3/nexttest.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, next, existsgoal));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}
