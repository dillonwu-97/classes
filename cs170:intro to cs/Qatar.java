public class Qatar {

    public static void main(String[] args) {
        // Test your classes  and methods here         
        Flight wee = new Flight("Helsinki" , "NY", 100);
        System.out.println(wee.getDeparture());
        System.out.println(wee.getDestination());
        System.out.println(wee.getQmiles());

        PrivilegeMember bread = new PrivilegeMember("Bread");
        bread.addFlight("NY", "SanFran", 1000);
        bread.addFlight("NY", "SanFran", 1000);
        System.out.println(bread.getName());
        System.out.println(bread.getTotalMiles());
    }

}

class Flight {

    // attributes
    private String departure; 
    private String destination; 
    private int qmiles;

    // constructor
    /**
     * creates a new flight. 
     * @param from - the city of departure for this flight
     * @param to - the destination for this flight
     * @param m - the number of miles obtained for this flight
     */
    public Flight(String from, String to, int m){
        // to be implemented
        departure = from;
        destination = to;
        qmiles = m;
    }
    
    // methods
    /**
     * @return the city of departure for this flight
     */
    public String getDeparture(){
        // to be implemented
        return departure;
    }

    /**
     * @return the destination for this flight
     */
    public String getDestination(){
        // to be implemented
        return destination;
    }

    /**
     * @return the number of miles for this flight
     */
    public int getQmiles(){
        // to be implemented
        return qmiles;
    }

    /**
     * @return flight information in the following format:
     *   departure -> destination (qmiles)
     */
    public String toString(){
        // to be implemented
        return departure + "->" + destination + "(" + qmiles + ")";
    }

}


class PrivilegeMember {

    private String name;
    private Flight[] record;
    private int numFlights;
    
    /**
     * Creates a new member with an empty list of flights.
     * Note: a member will never fly more than 5000 times.
     * @param n - the member's name
     */
    public PrivilegeMember(String n){
        // to be implemented
        name = n;
        record = new Flight[5000];
        numFlights = 0;
        
    }
    
    /**
     * @return the member's name
     */
    public String getName(){
        // to be implemented
        return name;
    }
    
    /**
     * @return the member's list of flights
     */
    public Flight[] getFlights(){
        // to be implemented
        return record;
    }
    
    /**
     * Creates a new flight and adds it to the member's list of flights
     * @param city1 - the city of departure
     * @param city2 - the destination
     * @param miles - the number of miles
     */
    public void addFlight(String city1, String city2, int miles){
        // to be implemented
        Flight flying = new Flight(city1, city2, miles);
        record[numFlights] = flying;
        numFlights++;
    }
    
    /**
     * @return the total number of miles accumulated by this member
     *         based on all the flights recorded
     */
    public int getTotalMiles(){
        // to be implemented        
        int last = 0;
        for (int i = 0; i < numFlights; i++) {
            last += record[i].getQmiles();
        }
        return last;
    }
    
}