import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;

public class nCk {

    @Before
    public void before() {
    set(v0, 1337);
    }

    /******************************************************************
     *
     * Test makes10
     *
     *****************************************************************/

    @Test 
    public void nCk_5_3() {
    run("nCk", 5, 3);
    Assert.assertEquals(10, get(v0));
    }

    @Test
    public void nCk_10_4() {
        run("nCk", 10, 4);
        Assert.assertEquals(210, get(v0));
    }

    @Test 
    public void nCk_100_4() {
    run("nCk", 100, 4);
    Assert.assertEquals(3921225, get(v0));
    }

    /******************************************************************
     *
     * Write many more tests!  * Test Edge Cases *
     *
     *****************************************************************/
    
    @Test
    public void nCk_n_lt_k() {
        run("nCk", 5, 10);
        Assert.assertEquals(-1, get(v0));
    }

    @Test
    public void nCk_n_0() {
        run("nCk", 5, 0);
        Assert.assertEquals(1, get(v0));
    }

    @Test
    public void nCk_n_1() {
        run("nCk", 6, 1);
        Assert.assertEquals(6, get(v0));
    }

    @Test
    public void nCk_n_n() {
        run("nCk", 5, 5);
        Assert.assertEquals(1, get(v0));
    }

    @Test
    public void nCk_negative() {
        run("nCk", -1, 10);
        Assert.assertEquals(-1, get(v0));
        run("nCk", 10, -1);
        Assert.assertEquals(-1, get(v0));
    }
} // end class


