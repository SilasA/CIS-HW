import org.junit.Assert;
import org.junit.Test;
import java.util.Random;
import static edu.gvsu.dlunit.DLUnit.*;

/**
 * Sample test cases for an signed, 16-bit adder with a carry-in and overflow.
 * IMPORTANT:  These test cases do *not* thoroughly test the adder.  You need to
 * re-name this class and add more tests!
 */
public class SampleSigned16BitAdderTest {


  // The complete list of integers to be tests.
  // (IMPORTANT !!! You need to add to this list !!!)
  public static final long testIntegers[] = {-32768, -32767, 0, 1, 2, 13, 127, 128, 129, 0x5555, 32766, 32767};
  public static final Random random = new Random();
	

  // Helper method that runs a test for a given pair of integers and a carryIn.
  protected static void verify(long a, long b, boolean carryIn) {

    //////////////////////////////////
    //
    // Compute the expected outputs
    //
    /////////////////////////////////
    long carryInAsInt = (carryIn ? 1 : 0);   // convert the Boolean to 0 or 1
    long expected = a + b + carryInAsInt;    // expected output value

    // The `overflow` output should be `true` if the expected output is not in the range [-(2^15), (2^15)-1]
    // (In java "1 << 15" takes the bit string 0000000000000001 and shifts it left 15 spaces, effectively
    // generating the value 2^15.)
    boolean expectedOverflow = ((expected >= (1 << 15)) || (expected < -(1 << 15)));

    // Output "wraps around" if there is an overflow
    if (expectedOverflow && expected > 0) {
      expected -= 65536;
    } else if (expectedOverflow && expected < 0) {
      expected += 65536;
    }

    ////////////////////////////////////////
    //
    // Configure and simulate the circuit
    //
    ///////////////////////////////////////
    setPinSigned("InputA", a);
    setPinSigned("InputB", b);
    setPin("CarryIn", carryIn);
    run();


    ////////////////////////////////////////
    //
    // Check the correctness of the output
    //
    ///////////////////////////////////////
    String message = "of " + a + " + " + b + " with " + (carryIn ? "a " : " no ") + " carry in";
    Assert.assertEquals("Output " + message, expected, readPinSigned("Output"));
    Assert.assertEquals("Overflow " + message, expectedOverflow, readPin("Overflow"));
  }

  //
  // Quick tests designed to quickly catch major errors.  (Also serve as example tests)
  // Testing all edge cases such as max, 0, and min values in different combinations.
  //

  @Test
  public void zero_zero_false() {
    verify(0, 0, false);
  }

  @Test
  public void zero_one_false() {
    verify(0, 1, false);
  }

  @Test
  public void zero_zero_true() {
    verify(0, 0, true);
  }

  @Test
  public void zero_one_true() {
    verify(0, 1, true);
  }
  
  @Test
  public void max_max_false() {
	verify(Short.MAX_VALUE, Short.MAX_VALUE, false);
  }
  
  @Test
  public void max_max_true() {
	verify(Short.MAX_VALUE, Short.MAX_VALUE, true);
  }
  
  @Test
  public void min_min_false() {
	verify(Short.MIN_VALUE, Short.MIN_VALUE, false);
  }
  
  @Test
  public void min_min_true() {
	verify(Short.MIN_VALUE, Short.MIN_VALUE, true);
  }
  
  @Test
  public void max_min_false() {
	verify(Short.MAX_VALUE, Short.MIN_VALUE, false);
  }
  
  @Test
  public void min_max_true() {
	verify(Short.MIN_VALUE, Short.MAX_VALUE, true);
  }

  // This is actually rather gross; but, it is an effective way to thoroughly test your adder without
  // having to write hundreds of individual methods.
  @Test
  public void testAll() {
    for (long a : testIntegers) {
      for (long b : testIntegers) {
        verify(a, b, false);
        verify(a, b, true);
      }
    }
  } // end testAll
  
  // Test a load of semi-random pairs of integers starting at a=-2^15 and b=2^15 - 1
  // This takes about 60 to 90 seconds assuming each 2 verify calls is ~1ms
  // This also prinds the values and the individual tests durations and then
  // The overall duration of the whole test when finished.
  @Test
  public void testRandomSeries() {
	long overallStart = System.currentTimeMillis();
    for (long a = Short.MIN_VALUE ; a <= Short.MAX_VALUE; a+= random.nextInt(300) + 113) {
      for (long b = Short.MAX_VALUE; b >= Short.MIN_VALUE; b-= random.nextInt(300) + 113) {
		long start = System.currentTimeMillis();
        verify(a, b, false);
        verify(a, b, true);
		long end = System.currentTimeMillis();
		System.out.println("a " + a + "b " + b + ": " + (end - start) + "ms");
      }
    }
	long overallEnd = System.currentTimeMillis();
	System.out.println("Test Duration: " + ((overallEnd - overallStart) / 1000) + "s");
  }
}
