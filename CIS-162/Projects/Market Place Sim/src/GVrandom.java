import java.util.*;
/***********************************************************
This class extends Random.  Therefore, all methods 
provided in the Random class can also be invoked on
a GVrandom object.  However, an additional method is provided
in GVrandom to generate random number about a desired
mean using a modified gaussian distribution.
 
@author Scott Grissom
@version Oct 1, 2013
*********************************************************/
public class GVrandom extends Random{

    // nothing needs to happen in the constructor
    public GVrandom(){
    }

/*********************************************************
Generates random numbers using a poisson distribiution.
@param mean the desired mean
*********************************************************/     
    public double nextPoisson(double mean){
        double nextTime = -Math.log(1.0 - nextDouble()) / (1.0/mean);
        return nextTime;
    }    

/*********************************************************
Main method used for testing purposes
*********************************************************/     
    public static void main(String args[]){
        GVrandom r = new GVrandom();
        double total = 0, max = 0.0, min = 1000;
        int MAX = 1000;
        double AVG = 5.0;
        for(int i=1; i<= MAX; i++){
            double val = r.nextPoisson(AVG);
            min = val < min ? val : min;
            max = val > max ? val : max;
            System.out.println(val);
            total += val;
        }
        
        System.out.println("Expected: " + AVG);
        System.out.println("Average: " + total / MAX);
        System.out.println("Min: " + min);
        System.out.println("Max: " + max); 
    }
    
/*********************************************************
Generates random numbers about a desired mean using a
modifed gaussian distriution.
@param mean the desired mean
*********************************************************/ 
    // modifies the normal curve by a ratio
    // private final double SD = 0.5;
    
//     public double nextNormal(double mean)
//     {
//         double SD = mean * 0.5;
//         double nextTime = SD * nextGaussian() + mean;
//         return nextTime;
//     }    
}
