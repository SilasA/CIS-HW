/***********************************************************************
 * Implements Square and Cube with hard coded arithmetic.
 *
 * @author Silas Agnew
 * @version 17 April 2018
 **********************************************************************/

public class MyMath1 implements MyMathInterface
{
    @Override
    public double square(double x) {
        return x * x;
    }

    @Override
    public double cubic(double x) {
        return x * x * x;
    }
}
