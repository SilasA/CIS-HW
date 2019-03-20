/***********************************************************************
 * Implements Square and Cube with Java Math utilities.
 *
 * @author Silas Agnew
 * @version 17 April 2018
 **********************************************************************/

public class MyMath2 implements MyMathInterface
{
    @Override
    public double square(double x) {
        return Math.pow(x, 2);
    }

    @Override
    public double cubic(double x) {
        return Math.pow(x, 3);
    }
}