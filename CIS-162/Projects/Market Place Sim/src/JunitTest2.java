 import static org.junit.Assert.*;
 import org.junit.*;
 
 public class JunitTest2{
    private MarketPlace theStore;
    private double TOLERANCE = 0.1; 
    
    /******************************************************
     * Instantiate a Market Place object.
     *
     * Called before every test case method.
     *****************************************************/
    @Before
    public void setUp()
    {
        theStore = new MarketPlace();  
    }

    /******************************************************
     * Test initial values of the constructor
     *****************************************************/
    @Test 
    public void testConstructor(){

         // confirm the default number of tellers
         Assert.assertEquals("MarketPlace(): should start with 3 tellers ", 
                 3, theStore.getNumCashiers());                  
         // confirm the default service time
         Assert.assertEquals("MarketPlace(): should start with 6.6 service time  ", 
                 6.6, theStore.getServiceTime(), TOLERANCE);                  
         // confirm the default arrival time
         Assert.assertEquals("MarketPlace(): should start with 2.5 arrival time ", 
                 2.5, theStore.getArrivalTime(), TOLERANCE);                  

    } 

    /******************************************************
     * Test set parameters
     *****************************************************/
    @Test 
    public void testSetParameters(){
        theStore.setParameters(6, 14.5, 4.8, false);
    
        // confirm cashiers changed
        Assert.assertEquals("Number of cashiers should be changed", 
                6, theStore.getNumCashiers()); 
        // confirm arrival time
        Assert.assertEquals("Arriva time should be changed", 
                4.8, theStore.getArrivalTime(), TOLERANCE); 
        // confirm cashiers
        Assert.assertEquals("Service time should be changed", 
                14.5, theStore.getServiceTime(), TOLERANCE); 
    }  
 
    /******************************************************
     * Test public methods
     *****************************************************/
    @Test 
    public void testPublicMethods(){
        theStore.getReport();
        theStore.getArrivalTime();
        theStore.getServiceTime();  
        theStore.getNumCustomersServed();   
        theStore.getLongestLineLength();
        theStore.getAverageWaitTime();            
    }
    
    /******************************************************
     * Test larger arrival times
     *****************************************************/
    @Test 
    public void testLargerArrivalTimes(){
        theStore.setParameters(3,12, 4, false);
        theStore.startSimulation();
        int previous = theStore.getNumCustomersServed();

        theStore.setParameters(3, 12, 6, false);
        theStore.startSimulation();
        int current = theStore.getNumCustomersServed();        
        Assert.assertTrue("Should be fewer customers given larger arrival times", 
                previous > current); 
    }

    /******************************************************
     * Test larger arrival times
     *****************************************************/
    @Test 
    public void testLargerServiceTimes(){
        theStore.setParameters(3,12, 4, false);
        theStore.startSimulation();
        double previous = theStore.getAverageWaitTime();

        theStore.setParameters(3, 16, 4, false);
        theStore.startSimulation();
        double current = theStore.getAverageWaitTime();        
        Assert.assertTrue("Should be longer wait given larger service times", 
                previous < current); 
    }    

    /******************************************************
     * Test reset values
     *****************************************************/
    @Test 
    public void testResetParameters(){
        theStore.setParameters(5, 14.5, 4.8, false);
        theStore.startSimulation();
        theStore.startSimulation();
        
        // confirm cashiers
        Assert.assertEquals("Number of cashiers should NOT be reset to default", 
                5, theStore.getNumCashiers()); 
    }  

    /******************************************************
     * Test format time
     *****************************************************/
    @Test 
    public void testFormatTime(){
         Assert.assertEquals("formatTime(200)", 
                 "3:20am",theStore.formatTime(200));                  
         Assert.assertEquals("formatTime(722)", 
                 "12:02pm",theStore.formatTime(722));   
         Assert.assertEquals("formatTime(915)", 
                 "3:15pm",theStore.formatTime(915));  
         Assert.assertEquals("formatTime(5)", 
                 "12:05am",theStore.formatTime(5));  
                
    }
    
    /******************************************************
     * Test with many tellers
     *****************************************************/
    @Test 
    public void testWithManyCashiers(){
        theStore.setParameters(10, 4.5, 4.8, false);
        theStore.startSimulation();
        
        // confirm cashiers
        Assert.assertTrue("There should be no wait line with plenty of cashiers", 
                theStore.getLongestLineLength() < 2); 
        Assert.assertTrue("Average wait should be close to zero with many cashiers", 
                theStore.getAverageWaitTime() < 1); 
    }   
    
    /******************************************************
     * Test fewer cashiers
     *****************************************************/
    @Test 
    public void testFewerCashiers(){
        theStore.setParameters(2, 8, 4, false);
        theStore.startSimulation();
        int previous = theStore.getLongestLineLength();

        theStore.setParameters(4, 8, 4, false);
        theStore.startSimulation();
        int current = theStore.getLongestLineLength();        
        Assert.assertTrue("Should be shorter wait line with more cashiers", 
                previous > current); 
    }
     
}     