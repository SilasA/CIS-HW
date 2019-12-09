import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;

public class BranchTest {


    /******************************************************************
     *
     * Test makes10
     *
     *****************************************************************/

    @Test 
    public void makes10_aIs10() {
	run("makes10", 10, 3);
	Assert.assertEquals(1, get(v0));
    }

    @Test
    public void makes10_bIs10() {
        run("makes10", 5, 10);
        Assert.assertEquals(1, get(v0));
    }

    @Test
    public void makes10_bothIs10() {
        run("makes10", 10, 10);
        Assert.assertEquals(1, get(v0));
    }

    @Test
    public void makes10_addIs10() {
        run("makes10", 5, 5);
        Assert.assertEquals(1, get(v0));
    }

    @Test
    public void makes10_not10() {
        run("makes10", 1, 4);
        Assert.assertEquals(0, get(v0));
    }


    /******************************************************************
     *
     * Test intMax
     *
     *****************************************************************/

    @Test 
    public void intMax_ascending() {
	run("intMax", 5, 6, 7);
	Assert.assertEquals(7, get(v0));
    }

    @Test 
    public void intMax_descending() {
	run("intMax", 7, 6, 5);
	Assert.assertEquals(7, get(v0));
    }

    @Test 
    public void intMax_middle() {
	run("intMax", 5, 8, 7);
	Assert.assertEquals(8, get(v0));
    }

    @Test 
    public void intMax_equal() {
	run("intMax", 6, 6, 6);
	Assert.assertEquals(6, get(v0));
    }

    /******************************************************************
     *
     * Test close10
     *
     *****************************************************************/

    @Test 
    public void close10_aClosest() {
	run("close10", 8, 13);
	Assert.assertEquals(8, get(v0));
    }

    @Test 
    public void close10_bClosest() {
	run("close10", 13, 8);
	Assert.assertEquals(8, get(v0));
    }

    @Test 
    public void close10_bothClosest() {
	run("close10", 8, 12);
	Assert.assertEquals(0, get(v0));
    }

    @Test 
    public void close10_equal() {
	run("close10", 8, 8);
	Assert.assertEquals(0, get(v0));
    }

    /******************************************************************
     *
     * Test dateFashion
     *
     *****************************************************************/

    @Test 
    public void dateFashion_youOk_dateStylish() {
	run("dateFashion", 5, 10);
	Assert.assertEquals(2, get(v0));
    }

    @Test 
    public void dateFashion_youStylish_dateOk() {
	run("dateFashion", 10, 5);
	Assert.assertEquals(2, get(v0));
    }

    @Test 
    public void dateFashion_youOk_dateOk() {
	run("dateFashion", 5, 5);
	Assert.assertEquals(1, get(v0));
    }

    @Test 
    public void dateFashion_youNo_dateStylish() {
	run("dateFashion", 2, 10);
	Assert.assertEquals(0, get(v0));
    }

    @Test 
    public void dateFashion_youOk_dateNo() {
	run("dateFashion", 5, 2);
	Assert.assertEquals(0, get(v0));
    }

} // end class

