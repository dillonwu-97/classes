public class TestAthletes {

    public static void main(String[] args) {
        // Uncomment the following lines to activate the test cases

        Marathoner angela = new Marathoner("Angela");
        System.out.println(angela);
        System.out.println("Name: " + angela.getName());
        angela.register("Speedy Peach", "road");
        angela.register("Fox Trot", "trail");
        angela.register("Thirsty Stroll", "desert");
        System.out.println("Location of Speedy Peach: " + angela.getLocation("Fox Trot"));
        System.out.println("Type of Speedy Peach: " + angela.getType("Fox Trot"));
        System.out.println("Location of Fox Trot: " + angela.getLocation("Fox Trot"));
        System.out.println("Type of Fox Trot: " + angela.getType("Fox Trot"));
        System.out.println("Location of Thirsty Stroll: " + angela.getLocation("Thirsty Stroll"));
        System.out.println("Type of Thirsty Stroll: " + angela.getType("Thirsty Stroll"));
        String[] angelaRaces = angela.getRaces();
        for (int i = 0; i < angelaRaces.length; i++) {
            System.out.println(angelaRaces[i]);
        }
        System.out.println("Completed runs: " + angela.getCompletedRuns());
        System.out.println("Completed races: " + angela.getCompletedRaces());
        angela.compete("Speedy Peach");
        System.out.println("Completed runs: " + angela.getCompletedRuns());
        System.out.println("Completed races: " + angela.getCompletedRaces());
        angela.compete("Fox Trot");
        System.out.println("Completed runs: " + angela.getCompletedRuns());
        System.out.println("Completed races: " + angela.getCompletedRaces());
        angela.compete("Thirsty Stroll");
        System.out.println("Completed runs: " + angela.getCompletedRuns());
        System.out.println("Completed races: " + angela.getCompletedRaces());
        System.out.println(angela);
        
        System.out.println("----------");
        Triathlete steve = new Triathlete("Steve");
        System.out.println(steve);
        System.out.println("Name: " + steve.getName());
        steve.register("Speedy Peach", "road");
        steve.register("Fishy Splash", "lake");
        steve.register("Kayak Ride", "river");
        System.out.println("Location of Speedy Peach: " + steve.getLocation("Speedy Peach"));
        System.out.println("Type of Speedy Peach: " + steve.getType("Speedy Peach"));
        System.out.println("Location of Fishy Splash: " + steve.getLocation("Fishy Splash"));
        System.out.println("Type of Fishy Splash: " + steve.getType("Fishy Splash"));
        System.out.println("Location of Kayak Ride: " + steve.getLocation("Kayak Ride"));
        System.out.println("Type of Kayak Ride: " + steve.getType("Kayak Ride"));
        String[] steveRaces = steve.getRaces();
        for (int i = 0; i < steveRaces.length; i++) {
            System.out.println(steveRaces[i]);
        }
        System.out.println("Completed runs: " + steve.getCompletedRuns());
        System.out.println("Completed swims: " + steve.getCompletedSwims());
        System.out.println("Completed races: " + steve.getCompletedRaces());
        steve.compete("Speedy Peach");
        System.out.println("Completed runs: " + steve.getCompletedRuns());
        System.out.println("Completed swims: " + steve.getCompletedSwims());
        System.out.println("Completed races: " + steve.getCompletedRaces());
        steve.compete("Fishy Splash");
        System.out.println("Completed runs: " + steve.getCompletedRuns());
        System.out.println("Completed swims: " + steve.getCompletedSwims());
        System.out.println("Completed races: " + steve.getCompletedRaces());
        steve.compete("Kayak Ride");
        System.out.println("Completed runs: " + steve.getCompletedRuns());
        System.out.println("Completed races: " + steve.getCompletedRaces());
        System.out.println(steve);
    }

}


abstract class Athlete {
    
    protected String name; // name of the athlete
    
    protected int numRaces; // number of races the athlete has registered for

    protected String[] raceNames; // names of the registered races
    
    protected String[] raceLocations; // locations of the registered races.
                                      // Possible values: "road", "trail", "pool", "lake"
    
    // Constructor
    // Assume that an athlete can register for no more than 1000 races.
    // name: the name of this athlete
    public Athlete(String name) {
        this.name = name;
        numRaces = 0;
        raceNames = new String[1000];
        raceLocations = new String[1000];
        // You implement this method
    }
    
    // Returns the name of this athlete
    public String getName() {
        // You implement this method
        return name;
    }
    
    // Returns the location of the given race,
    // or null if the athlete is not registered to this race.
    public String getLocation(String raceName) {
        // You implement this method
        for (int i = 0; i < numRaces; i++) {
            if (raceNames[i].equals(raceName)) {
                return raceLocations[i];
            }
        }
        return null;
       
    }
    
    // Returns the type of race based on the location:
    // "run" if the location is "road" or "trail"
    // "swim" if the location is "pool" or "lake"
    // "unknown" if the location is anything else
    // Returns null if the athlete is not registered to this race.
    public String getType(String raceName) {
        // You implement this method
        for (int i = 0; i < numRaces; i++) {
            if (raceNames[i].equals(raceName)) {
                if (raceLocations[i].equals("road") || raceLocations[i].equals("trail")) {
                    return "run";
                } else if (raceLocations[i].equals("pool") || raceLocations[i].equals("lake")) {
                    return "swim";
                } else {
                    return "unknown";
                }
            }         
        }
        return null;
    }
    
    // Returns a new array describing the registered races.
    // Each String in the array has the following format:
    //   "<raceName> at <location>"
    // The new array that is returned has exactly numRaces slots.
    public String[] getRaces() {
        String[] result = new String[numRaces];
        for (int i = 0; i < numRaces; i++) {
            result[i] = raceNames[i] + " at " + raceLocations[i];
        }
        return result;
    }
    
    // Adds a new race to the list of registered races for this athlete.
    // raceName: name of the new race to be added
    // location: place of the new race to be added
    public void register(String raceName, String location) {
        // You implement this method
        raceNames[numRaces] = raceName;
        raceLocations[numRaces] = location;
        numRaces++;
    }
    
    // Compete in the race with the given name.
    public abstract boolean compete(String raceName);
    
    // Returns the total number of races that this athlete has completed.
    // To complete a race the athlete must compete in it.
    public abstract int getCompletedRaces();

}


interface Runner {

    public String run(String race, String location);

}


interface Swimmer {

    public String swim(String race, String location);   

}


class Marathoner extends Athlete {
    
    private int completedRuns; // number of completed runs
    
    // Constructor
    public Marathoner(String name) {
        // You implement this method
        super(name);
        completedRuns = 0;
    }
    
    // Returns the number of completed runs
    public int getCompletedRuns() {
        // You implement this method
        return completedRuns;
    }
    
    // Returns a string representation of this object in the format:
    // "My name is <name> and I am a Marathoner. I completed <num_completed_runs> runs."
    public String toString() {
        // You implement this method
        return "My name is " + name + " and I am a Marathoner. I completed " + completedRuns + " runs."; // delete this
    }
    
    // Competes in the race with the given name.
    // If the athlete was registered to that race and the race is a run, then:
    // 1. runs the race and prints the returned message
    // 2. increases the number of completed runs
    // 3. prints the message "I completed the run <race_name>"
    // 4. returns true.
    // Otherwise the method prints "I cannot run <race_name>" and returns false.
    public boolean compete(String raceName) {
        // You implement this method
        for (int i = 0; i < numRaces; i++) {
            if (raceNames[i].equals(raceName) && getType(raceName).equals("run")) {
                System.out.println(run(raceName, raceLocations[i]));
                System.out.println("I completed the run " + raceName);
                completedRuns++;
                return true;
            }
        }
        System.out.println("I cannot run " + raceName);
        return false;
    }

    // Returns the total number of completed races.
    // For a Marathoner, it is the number of completed runs.
    public int getCompletedRaces() {
        return completedRuns;
    }

    // Returns the message: "I am a Marathoner and I am running <race> on the <location>"
    public String run(String race, String location) {
        return "I am a Marathoner and I am running " + race + " on the " + location;
    }

}


class Triathlete extends Athlete {
    
    private int completedRuns;  // number of completed runs
    
    private int completedSwims;  // number of completed swims
    
    // Constructor
    public Triathlete(String name) {
        super(name);
        completedSwims = 0;
        completedRuns = 0;
        // You implement this method
    }
    
    // Returns the number of completed runs
    public int getCompletedRuns() {
        return completedRuns;
    }
    
    // Returns the number of completed swims
    public int getCompletedSwims() {
        return completedSwims;
    }
    
    // Returns a string representation of this object in the format:
    // "My name is <name> and I am a Triathlete. I completed<num_completed_runs> runs and <num_completed_swims> swims."    
    public String toString() {
        return "My name is " + name + " and I am a Triathlete. I completed " + completedRuns + " runs and " +
         completedSwims + " swims.";
    }
    
    // Competes in the race with the given name.
    // If the athlete was registered to that race and the race is a run, then:
    // 1. runs the race and prints the returned message
    // 2. increases the number of completed runs
    // 3. prints the message "I completed the run <race_name>"
    // 4. returns true.
    // If the athlete was registered to that race and the race is a swim, then:
    // 1. swims the race and prints the returned message
    // 2. increases the number of completed swims
    // 3. prints the message "I completed the swim <race_name>"
    // 4. returns true.
    // Otherwise the method prints "I cannot run or swim <race_name>" and returns false.
    public boolean compete(String raceName) {
        for (int i = 0; i < numRaces; i++) {
            if (raceNames[i].equals(raceName) && getType(raceName).equals("run")) {
                System.out.println(run(raceName, raceLocations[i]));
                completedRuns++;
                System.out.println("I completed the run " + raceName);
                return true;
            } else if (raceNames[i].equals(raceName) && getType(raceName).equals("swim")) {
                System.out.println(swim(raceName, raceLocations[i]));
                completedSwims++;
                System.out.println("I completed the swim " + raceName);
                return true;
            }
        }
        System.out.println("I cannot run or swim " + raceName);
        return false;
    }
    
    public int getCompletedRaces() {
        return completedSwims + completedRuns;
    }

    // Returns the message: "I am a Triathlete and I am running <race> on the<location>"
    public String run(String race, String location) {
        return "I am a Triathlete and I am running " + race + " on the " + location;
    }

    // Returns the message: "I am a Triathlete and I am swimming <race> in the<location>"
    public String swim(String race, String location) {
        return "I am a Triathlete and I am swimming " + race + " on the " + location;
    }

}