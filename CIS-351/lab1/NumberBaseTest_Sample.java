import org.junit.Assert;
import org.junit.Test;

/**
 * Example (but nowhere near complete) unit tests for the NumberBase.conversions method
 *
 * @author Zachary Kurmas
 */
// Created  8/26/13 at 11:48 AM
// (C) Zachary Kurmas 2013
//
// Modified by Jared Moore 2016

public class NumberBaseTest_Sample {

   @Test
   public void decimal_to_binary() throws Throwable {
      System.out.println(NumberBase_Stub.convert("0", 10, 2));
      Assert.assertEquals("0", NumberBase_Stub.convert("0", 10, 2));
      Assert.assertEquals("0", NumberBase_Stub.convert("0", 2, 10));
      Assert.assertEquals("1", NumberBase_Stub.convert("1", 10, 2));
      Assert.assertEquals("1010", NumberBase_Stub.convert("10", 10, 2));
      Assert.assertEquals("1000011110001", NumberBase_Stub.convert("4337", 10, 2));
   }

   @Test
   public void binary_to_decimal() throws Throwable {
      Assert.assertEquals("10", NumberBase_Stub.convert("1010", 2, 10));
   }

   @Test
   public void decimal_to_hex() throws Throwable {
      Assert.assertEquals("64", NumberBase_Stub.convert("100", 10, 16));
      Assert.assertEquals("dead", NumberBase_Stub.convert("57005", 10, 16));
   }

   @Test
   public void hex_to_binary() throws Throwable {
      Assert.assertEquals("1010", NumberBase_Stub.convert("a", 16, 2));
   }

   @Test
   public void randoms_to_hex() throws Throwable {
      Assert.assertEquals("8bdf", NumberBase_Stub.convert("t82", 35, 16));
   }
   @Test
   public void assortedConversions() throws Throwable {
      Assert.assertEquals("46655", NumberBase_Stub.convert("zzz", 36, 10));
      Assert.assertEquals("zzz", NumberBase_Stub.convert("46655", 10, 36));
      Assert.assertEquals("zz", NumberBase_Stub.convert("zz", 36, 36));
   }

   // Remember:  When looking for an exception, you can do only *one* test
   // per method.
   @Test(expected = IllegalArgumentException.class)
   public void input_is_valid1() {
      NumberBase_Stub.convert("14d", 10, 2);
   }

   @Test(expected = IllegalArgumentException.class)
   public void input_is_valid2() {
      NumberBase_Stub.convert("3", 2, 6);
   }

   @Test(expected = IllegalArgumentException.class)
   public void input_is_valid3() {
      NumberBase_Stub.convert("z", 30, 2);
   }

   @Test(expected = IllegalArgumentException.class)
   public void input_is_valid4() {
      NumberBase_Stub.convert("X", 10, 2);
   }

   @Test(expected = IllegalArgumentException.class)
   public void input_is_valid5() {
      NumberBase_Stub.convert("$", 10, 2);
   }

   // Be sure to test other values and base pairs!
}
