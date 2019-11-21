package modelChecker;

import static org.junit.Assert.*;

import java.io.IOException;

import formula.stateFormula.And;
import org.junit.Test;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import modelChecker.ModelChecker;
import modelChecker.SimpleModelChecker;
import model.Model;

public class ModelCheckerTest {

    /*
     * An example of how to set up and call the model building methods and make
     * a call to the model checker itself. The contents of model.json,
     * constraint1.json and ctl.json are just examples, you need to add new
     * models and formulas for the mutual exclusion task.
     */
    @Test
    public void buildAndCheckModel() {
        try {
            Model model = Model.parseModel("src/test/resources/model1.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/true.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/test.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void mutexTest() {
        try {
            Model model = Model.parseModel("src/test/resources/mutex/model.json");

            StateFormula mutex = new FormulaParser("src/test/resources/mutex/mutex.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, mutex, mutex));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void strongFairTest() {
        try {
            Model model = Model.parseModel("src/test/resources/mutex/model.json");

            StateFormula strongFair = new FormulaParser("src/test/resources/mutex/strongfair.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, strongFair, strongFair));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void strongFairMutexTest() {
        try {
            Model model = Model.parseModel("src/test/resources/mutex/model.json");

            StateFormula mutex = new FormulaParser("src/test/resources/mutex/mutex.json").parse();
            StateFormula strongFair = new FormulaParser("src/test/resources/mutex/strongfair.json").parse();

            StateFormula constraint = new And(mutex, strongFair);


            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, constraint, strongFair));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void strongFairNoConstraintTest() {
        try {
            Model model = Model.parseModel("src/test/resources/mutex/model.json");

            StateFormula constraint = new FormulaParser("src/test/resources/true.json").parse();
            StateFormula strongFair = new FormulaParser("src/test/resources/mutex/strongfair.json").parse();


            ModelChecker mc = new SimpleModelChecker();

            //not true on its own
            assertFalse(mc.check(model, constraint, strongFair));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void strongFairNoMutex() {
        try {
            Model model = Model.parseModel("src/test/resources/mutex/model.json");

            StateFormula mutex = new FormulaParser("src/test/resources/mutex/mutex.json").parse();
            StateFormula strongFair = new FormulaParser("src/test/resources/mutex/strongfair.json").parse();


            ModelChecker mc = new SimpleModelChecker();

            //not true on its own
            assertFalse(mc.check(model, strongFair, mutex));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}
