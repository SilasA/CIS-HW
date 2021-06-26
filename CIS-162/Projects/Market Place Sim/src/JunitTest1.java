 import static org.junit.Assert.*;
 import org.junit.*;
 /****************************************************
  * MAKE NO CHANGES TO THIS CODE
  * This class is used for automatically testing Part 1
  * of the MarketPlace simulation.
  ***************************************************/
 public class JunitTest1{
    private MarketPlace theStore;
    private Customer cust;
    private GVarrival arrive;
    private GVdeparture depart;
    
    private double TOLERANCE = 0.1; 
    
    /******************************************************
     * Instantiate a Market Place object.
     *
     * Called before every test case method.
     *****************************************************/
    @Before
    public void setUp(){
        theStore = new MarketPlace();  
        cust = new Customer(30);
        arrive = new GVarrival(theStore, 45.7);
        depart = new GVdeparture(theStore, 45.7, 99);
    }

    /******************************************************
     * Test Customer methods
     *****************************************************/
    @Test 
    public void testCustomer(){
         Assert.assertEquals("Customer(): arrival time should be 30.0", 
                 30, cust.getArrivalTime(), TOLERANCE);

         cust.setArrivalTime(53.2);        
         Assert.assertEquals("Customer(): arrival time should be 53.2", 
                 53.2, cust.getArrivalTime(), TOLERANCE);                  
    } 
 
    /******************************************************
     * Test GVarrival methods
     *****************************************************/
    @Test 
    public void testArrival(){
         Assert.assertEquals("GVarrival(): time should be 45.7", 
                 45.7, arrive.getTime(), TOLERANCE);                  
    }     

    /******************************************************
     * Test GVdeparture methods
     *****************************************************/
    @Test 
    public void testDeparture(){
         Assert.assertEquals("GVdeparture(): time should be 45.7", 
                 45.7, depart.getTime(), TOLERANCE);                  
         Assert.assertEquals("GVdeparture(): cashier ID should be 99", 
                 99, depart.getCashier());                  
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
     * Confirm public methods exist
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
     
}     