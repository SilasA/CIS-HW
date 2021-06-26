/**
 * Write a description of class InLabTest here.
 * 
 * @author Silas Agnew
 * @version 17 April 2018
 */
public class InLabTest
{
    public static void test(MyMathInterface o1,MyMathInterface o2) {
        System.out.println("Diff between squares: "+
        (o1.square(3.0) - o2.square(3.0)));
        System.out.println("Diff between cubics: "+
        (o1.cubic(3.0) - o2.cubic(3.0)));
    }
    
    public static void main(String args[]) {
        MyMath1 mm1 = new MyMath1();
        MyMath2 mm2 = new MyMath2();
        test(mm1,mm2);
        test(mm1,mm1);
        test(mm2,mm2);
    }
}
