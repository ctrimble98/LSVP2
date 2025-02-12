package modelChecker;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ExampleTest {

    /*
     * An example of how to set up and call the model building methods and make
     * a call to the model checker itself. The contents of model.json,
     * constraint1.json and ctl.json are just examples, you need to add new
     * models and formulas for the mutual exclusion task.
     */
    @Test
    public void model1() {
        try {
            Model model = Model.parseModel("src/test/resources/model1/model1.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/model1/constraint1.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/model1/ctl1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void model1noConstraint() {
        try {
            Model model = Model.parseModel("src/test/resources/model1/model1.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/true.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/model1/ctl1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void model1atomicProp() {
        try {
            Model model = Model.parseModel("src/test/resources/model1/model1.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/true.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/model1/aptest.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void model2() {
        try {
            Model model = Model.parseModel("src/test/resources/model2/model2.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/model2/constraint2.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/model2/ctl2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void model2noConstraint() {
        try {
            Model model = Model.parseModel("src/test/resources/model2/model2.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/true.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/model2/ctl2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}
