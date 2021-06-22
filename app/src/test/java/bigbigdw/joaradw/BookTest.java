package bigbigdw.joaradw;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


public class BookTest {

    @Test
    public void sum_isCorrect() {
        Computation computation = new Computation();
        assertEquals(4, computation.Sum(2, 2));
    }

    @Test
    public void multiply_isCorrect() {
        Computation computation = new Computation();
        assertEquals(4, computation.Multiply(2, 2));
    }
}