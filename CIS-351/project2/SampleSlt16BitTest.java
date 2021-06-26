import org.junit.Assert;
import org.junit.Test;
import java.util.Random;

import static edu.gvsu.dlunit.DLUnit.*;
import static edu.gvsu.dlunit.DLUnit.readPin;

/**
 * Sample test cases for 16-bit set less than circuit
 * <p/>
 * IMPORTANT:  These test cases do *not* thoroughly test the circuit.  You need to
 * re-name this class and add more tests!
 * <p/>
 * Created by kurmasz on 8/8/16.
 */
public class SampleSlt16BitTest {

	public static Random random = new Random();

  public void verify(long a, long b, boolean signed) {

    if (signed) {
      setPinSigned("InputA", a);
      setPinSigned("InputB", b);
    } else {
      setPinUnsigned("InputA", a);
      setPinUnsigned("InputB", b);
    }

    setPin("Signed", signed);
    run();


    ////////////////////////////////////////
    //
    // Check the correctness of the output
    //
    ///////////////////////////////////////
    String message = String.format("%d < %d (%s): ", a, b, signed ? "signed" : "unsigned");
    Assert.assertEquals("Output " + message, a < b, readPin("LessThan"));
  }

  @Test
  public void zero_zero_signed() {
    verify(0, 0, true);
  }

  @Test
  public void zero_one_signed() {
    verify(0, 1, true);
  }

  @Test
  public void one_zero_signed() {
    verify(1, 0, true);
  }

  @Test
  public void zero_negone_signed() {
    verify(0, -1, true);
  }

  @Test
  public void negone_zero_signed() {
    verify(-1, 0, true);
  }

  @Test
  public void one_negone_signed() {
    verify(1, -1, true);
  }

  @Test
  public void negone_one_signed() {
    verify(-1, 1, true);
  }

  @Test
  public void zero_zero_unsigned() {
    verify(0, 0, false);
  }

  @Test
  public void zero_one_unsigned() {
    verify(0, 1, false);
  }

  @Test
  public void one_zero_unsigned() {
    verify(1, 0, false);
  }
  
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
		int ua = (int)(a + Short.MAX_VALUE + 1),
			ub = (int)(b + Short.MAX_VALUE + 1);
        verify(ua, ub, false);
        verify(a, b, true);
		long end = System.currentTimeMillis();
		System.out.println("a " + a + "b " + b + ": " + (end - start) + "ms");
      }
    }
	long overallEnd = System.currentTimeMillis();
	System.out.println("Test Duration: " + ((overallEnd - overallStart) / 1000) + "s");
  }
}
