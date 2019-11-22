package modelChecker;

import static org.junit.Assert.*;

import java.io.IOException;

import com.sun.org.apache.xpath.internal.operations.Bool;
import formula.stateFormula.And;
import formula.stateFormula.BoolProp;
import org.junit.Test;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import modelChecker.ModelChecker;
import modelChecker.SimpleModelChecker;
import model.Model;

public class MutexTest {

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

    @Test
    public void doubleMutex() {
        try {
            Model model = Model.parseModel("src/test/resources/mutex/model.json");

            StateFormula mutex = new FormulaParser("src/test/resources/mutex/mutex.json").parse();
            StateFormula mutex2 = new FormulaParser("src/test/resources/mutex/mutex2.json").parse();


            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, mutex, mutex2));
            model = Model.parseModel("src/test/resources/mutex/model.json");
            assertTrue(mc.check(model, mutex2, mutex));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void strongFairWeakFairMutex() {
        try {
            Model model = Model.parseModel("src/test/resources/mutex/model.json");

            StateFormula mutex = new FormulaParser("src/test/resources/mutex/mutex.json").parse();
            StateFormula weakFair = new FormulaParser("src/test/resources/mutex/weakfair.json").parse();
            StateFormula strongFair = new FormulaParser("src/test/resources/mutex/strongfair.json").parse();


            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, new And(mutex, strongFair), weakFair));
            model = Model.parseModel("src/test/resources/mutex/model.json");
            assertTrue(mc.check(model, new And(mutex, weakFair), strongFair));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void strongFairWeakFairNoMutex() {
        try {
            Model model = Model.parseModel("src/test/resources/mutex/model.json");

            StateFormula weakFair = new FormulaParser("src/test/resources/mutex/weakfair.json").parse();
            StateFormula strongFair = new FormulaParser("src/test/resources/mutex/strongfair.json").parse();


            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, strongFair, weakFair));
            model = Model.parseModel("src/test/resources/mutex/model.json");
            assertTrue(mc.check(model, weakFair, strongFair));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    /**
     * Test to show that our strong fair constraint is equivalent to the action based version
     */
    @Test
    public void strongFairMutexActTest() {
        try {
            Model model = Model.parseModel("src/test/resources/mutex/model.json");

            StateFormula mutex = new FormulaParser("src/test/resources/mutex/mutex.json").parse();
            StateFormula strongFair = new FormulaParser("src/test/resources/mutex/strongfair.json").parse();
            StateFormula strongFairAct = new FormulaParser("src/test/resources/mutex/strongfairact.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, strongFair, strongFairAct));
            model = Model.parseModel("src/test/resources/mutex/model.json");
            assertTrue(mc.check(model, strongFairAct, strongFair));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}
