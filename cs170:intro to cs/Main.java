public class Main{
    
    public static void main (String[] args){
        
        Flight f = new Flight("Doha", "Tokyo", 3600);
        //System.out.println(f.getDeparture());       
        
        PrivilegeMember member= new PrivilegeMember("Mickey Mouse");
        member.addFlight("Doha", "Tunis", 1200);
        member.addFlight("Paris", "Doha",4000);
        System.out.println(member.getFlights());
        
        //System.out.println(member.getTotalMiles()); 
    }
    
}

/* first class*/

class Flight {

    private String departure; 
    private String destination; 
    private int qmiles;
    
    /**
     * creates a new flight. 
     * @param from - the city of departure for this flight
     * @param to - the destination for this flight
     * @param m - the number of miles obtained for this flight
     */
    public Flight(String from, String to, int m){
        departure = from;
        destination = to;
        qmiles = m;
    }
    
    /**
     * 
     * @return the city of departure for this flight
     */
    public String getDeparture(){
        return departure;
    }
    
    /**
     * 
     * @return the destination for this flight
     */
    public String getDestination(){
        return destination;
    }
    
    /**
     * 
     * @return the number of miles for this flight
     */
    public int getQmiles(){
        return qmiles;
    }
    
    public String toString(){
        return departure + "->" + destination + "(" + qmiles + ")"; 
    }
    
}

/*  Second class */

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
        name = n;
        record = new Flight[5000];
        this.numFlights=0;
    }
    
    /**
     * 
     * @return the member's name
     */
    public String getName(){
        return name;
    }
    
    /**
     * 
     * @return the member's list of flights
     */
    public Flight[] getFlights(){
        return record;
    }
    
    /**
     * creates a new flight and adds it to the member's list of flights
     * @param city1 - the city of departure
     * @param city2 - the destination
     * @param miles - the number of miles
     */
    public void addFlight(String city1, String city2, int miles){
        Flight flight = new Flight(city1, city2, miles);
        record[numFlights]=flight;
        numFlights++;
    }
    
    /**
     * 
     * @return the total number of miles accumulated by this member based on all
the flights recorded
     */
    public int getTotalMiles(){
        int res = 0;
        for (int i=0; i<numFlights; i++){
            res+=record[i].getQmiles();
        }
        return res;
    }
    
}