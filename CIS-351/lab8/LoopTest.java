import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;

public class LoopTest {


    /******************************************************************
     *
     * Test indexOf
     *
     ******************************************************************/

    Label array1 = wordData(5, 4, 7, 6, 9, 8, 2, 1, -1);
    Label array2 = wordData(3, 40, 4, 5, 4, 3, 1, -1);

    @Test
    public void indexOf_findsValueOccurringOnceOnly() {
	run("indexOf", 2, array1);
	Assert.assertEquals(6, get(v0));
    }

    @Test
    public void indexOf_returns_neg1_ifNotFound() {
	run("indexOf", 3, array1);
	Assert.assertEquals(-1, get(v0));
    }

    @Test
    public void indexOf_findsValueOccurringMulti() {
        run("indexOf", 4, array2);
        Assert.assertEquals(2, get(v0));
    }

    /******************************************************************
     *
     * Test max
     *
     ******************************************************************/
    Label max_array1 = wordData(0, 0, 0, 0, 0, 0, 0);
    Label max_array2 = wordData(0, 3, 3, 5, 10, 11, 11, 4);
    Label max_array3 = wordData(-1, -2, -10);

    @Test
    public void max_findsMaximum() {
	run("max", array1, 10);
	Assert.assertEquals(9, get(v0));
    }

    @Test
    public void max_findMaxZero() {
        run("max", max_array1, 7);
        Assert.assertEquals(0, get(v0));
    }

    @Test
    public void max_findMaxMulti() {
        run("max", max_array2, 8);
        Assert.assertEquals(11, get(v0));
    }

    @Test
    public void max_findMaxNegative() {
        run("max", max_array3, 3);
        Assert.assertEquals(-1, get(v0));
    }

    /******************************************************************
     *
     * Test sum13
     *
     ******************************************************************/

    Label sum13_array1 = wordData(1, 2, 2, 1);
    Label sum13_array2 = wordData(1, 2, 13, 2, 1, 13);
    Label sum13_array3 = wordData(13, 13, 13, 1);
    Label sum13_array4 = wordData(0);
    Label sum13_array5 = wordData(-1, -3, 13, 4);
    Label empty = wordData();

    @Test
    public void sum13_sumsAllIfNo13() {
	run("sum13", sum13_array1, 4);
	Assert.assertEquals(6, get(v0));
    }

    @Test
    public void sum13_ignore13_andFollowing() {
	run("sum13", sum13_array2, 6);
	Assert.assertEquals(4, get(v0));
    }

    @Test
    public void sum13_sumOnly13() {
        run("sum13", sum13_array3, 4);
        Assert.assertEquals(0, get(v0));
    }

    @Test
    public void sum13_sumZero() {
        run("sum13", sum13_array4, 1);
        Assert.assertEquals(0, get(v0));
    }

    @Test
    public void sum13_sumNegative() {
        run("sum13", sum13_array5, 4);
        Assert.assertEquals(-4, get(v0));
    }

    @Test
    public void sum13_noValues() {
        run("sum13", empty, 0);
        Assert.assertEquals(0, get(v0));
    }

    /******************************************************************
     *
     * Test sum67
     *
     ******************************************************************/

    Label sum67_array1 = wordData(14, 6, 2, 3, 4, 7, 9, 10);
    Label sum67_array2 = wordData(1, 2, 2);
    Label sum67_array3 = wordData(1, 6, 7, 4, 6, -3, 2, 9, 7, -2);
    Label sum67_array4 = wordData(1, 6, 3, 6, 7, 3, 1, 6, 3);

    @Test
    public void sum67_handlesOneRun() {
	run("sum67", sum67_array1, 8);
	Assert.assertEquals(33, get(v0));
    }
    
    @Test
    public void sum67_noSkip() {
        run("sum67", sum67_array2, 3);
        Assert.assertEquals(5, get(v0));
    }

    @Test
    public void sum67_2Skip() {
        run("sum67", sum67_array3, 10);
        Assert.assertEquals(3, get(v0));
    }

    public void sum67_inner() {
        run("sum67", sum67_array4, 9);
        Assert.assertEquals(5, get(v0));
    }
}

