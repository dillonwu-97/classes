public class Fuel {

    public static void main(String[] args) {

        System.out.println("--- Test EmergencyGenerator ---");
        EmergencyGenerator x = new EmergencyGenerator();
        System.out.println(x);
        System.out.println("Fuel left percent: " + x.fuelLeft());
        x.fillTank();
        System.out.println("Fuel left percent: " + x.fuelLeft());
        double t1 = x.powerUp(0.5);
        System.out.println("Actual uptime: " + t1);
        System.out.println("Fuel left percent: " + x.fuelLeft());
        double t2 = x.powerUp(15);
        System.out.println("Actual uptime: " + t2);
        System.out.println("Fuel left percent: " + x.fuelLeft());

        System.out.println("--- Test Car ---");
        Car y = new Car(15, 30);
        System.out.println(y);
        System.out.println("Fuel left percent: " + y.fuelLeft());
        y.fillTank();
        System.out.println("Fuel left percent: " + y.fuelLeft());
        double d1 = y.drive(90);
        System.out.println("Actual miles driven: " + d1);
        System.out.println("Fuel left percent: " + y.fuelLeft());
        double d2 = y.drive(500);
        System.out.println("Actual miles driven: " + d2);
        System.out.println("Fuel left percent: " + y.fuelLeft());
        
        System.out.println("--- Test Bycicle ---");
        Bicycle z = new Bicycle();
        System.out.println(z);
        double d3 = z.drive(20);
        System.out.println("Actual miles driven: " + d3);
        double d4 = z.drive(150);
        System.out.println("Actual miles driven: " + d4);

        System.out.println("--- Test polymorphism ---");
        Refuelable r1 = new EmergencyGenerator();
        System.out.println(r1);
        Refuelable r2 = new Car(10, 35);
        System.out.println(r2);
        Driveable v1 = new Car(12, 32);
        System.out.println(v1);
        Driveable v2 = new Bicycle();
        System.out.println(v2);

        /* Expected output:
        --- Test EmergencyGenerator ---
        Emergency generator with 0.0 gallons of fuel
        Fuel left percent: 0.0
        Fuel left percent: 100.0
        My generator was on for 0.5 hours and consumed 0.1 gallons of fuel.
        Actual uptime: 0.5
        Fuel left percent: 95.0
        My generator was on for 9.5 hours and consumed 1.9 gallons of fuel.
        Actual uptime: 9.5
        Fuel left percent: 0.0
        --- Test Car ---
        Car with 0.0 out of 15.0 gallons of fuel; miles per gallon: 30.0
        Fuel left percent: 0.0
        Fuel left percent: 100.0
        I drove my car for 90.0 miles and consumed 3.0 gallons of fuel.
        Actual miles driven: 90.0
        Fuel left percent: 80.0
        I drove my car for 360.0 miles and consumed 12.0 gallons of fuel.
        Actual miles driven: 360.0
        Fuel left percent: 0.0
        --- Test Bycicle ---
        This is a bicycle
        I rode my bike for 20.0 miles and burned 600.0 calories.
        Actual miles driven: 20.0
        I rode my bike for 100.0 miles and burned 3000.0 calories.
        Actual miles driven: 100.0
        --- Test polymorphism ---
        Emergency generator with 0.0 gallons of fuel
        Car with 0.0 out of 10.0 gallons of fuel; miles per gallon: 35.0
        Car with 0.0 out of 12.0 gallons of fuel; miles per gallon: 32.0
        This is a bicycle
        */
    }
}

interface Refuelable {
    
    // Refills a tank to its maximum capacity.
    public void fillTank();
    
    // Returns the percentage (0 to 100) of fuel left.
    public double fuelLeft();

}

interface Driveable {

    // Drives up to the specified number of miles if possible.
    // Returns the actual number of miles driven.
    public double drive(double miles);
    
}

class EmergencyGenerator implements Refuelable {
    
    // Every generator has a tank with 2 gallons capacity.
    // With a full tank, the generator can stay on for up to 10 hours.

    private double tank; // gallons of fuel in the tank

    // constructor
    public EmergencyGenerator () {
        tank = 0;
    }

    // Powering up a generator prints a message with the format:
    // "My generator was on for ___ hours and consumed ___ gallons of fuel."
    // The amount of fuel in the tank is reduced accordingly.
    // If there is not enough fuel to power up the generator for the requested time,
    // the actual number of hours the generator stays on will be smaller.

    public void fillTank() {
        tank = 2;
    }

    public double fuelLeft() {
        return tank / 2 * 100;
    }

    public double powerUp(double hour) {
        double fuel = hour / 5; // tells me fuel
        if (tank < fuel) {
            fuel = tank;
            hour = tank * 5;
        }
        System.out.println("My generator was on for " + hour + " hours and consumed " + fuel + " gallons of fuel.");
        tank = tank - fuel;
        return hour;
    }
    
    // The toString() method returns a String with the format:
    // "Emergency generator with ___ gallons of fuel"

    public String toString() {
        return "Emergency generator with " + tank + " gallons of fuel";
    }

    
}

class Car implements Driveable, Refuelable {

    private double tank;  // gallons of fuel in the tank
    
    private double tankCapacity; // maximum gallons of fuel
    
    private double milesPerGallon; // fuel consumed for each mile driven

    public Car(double tankCapactiy, double milesPerGallon) {
        tank = 0;
        this.tankCapacity = tankCapactiy;
        this.milesPerGallon = milesPerGallon;
    }
    // Driving a car prints a message with the format:
    // "I drove my car for ___ miles and consumed ___ gallons of fuel."
    // The amount of fuel in the tank is reduced accordingly.
    // If there is not enough fuel to drive the requested number of miles,
    // the actual number of driven miles will be smaller.

    public void fillTank() {
        tank = tankCapacity;
    }

    public double fuelLeft() {
        double tankPercent = tank / tankCapacity * 100;
        return tankPercent;
    }

    public double drive(double distance) {
        double result = tank;
        double resultDis = distance;
        double gallons = distance / milesPerGallon;
        if (gallons > tank) {
            result = result - tank; 
            gallons = tank;
            resultDis = tank * milesPerGallon;
        } else {
            result = tank - gallons;
        }
        tank = result;
        System.out.println("I drove my car for " + resultDis + " miles and consumed " + gallons + " gallons of fuel.");
        return resultDis;
    }


    // The toString() method returns a String with the format:
    // "Car with ___ out of ___ gallons of fuel; miles per gallon: ___ "

    public String toString() {
        return "Car with " + tank + " out of " + tankCapacity + " gallons of fuel; miles per gallon: " + milesPerGallon;
    }

}

class Bicycle implements Driveable{

    // Driving a bike prints a message with the format:
    // "I rode my bike for ___ miles and burned ___ calories."
    // Riding a bike burns 30 calories per mile.
    // You can ride a bike for up to 100 miles consecutively.

    public double drive (double miles) {
        double result = miles * 30; // calories burned
        if (miles > 100) {
            result = 3000;
            miles = 100;
        }
        System.out.println("I rode my bike for " + miles + " miles and burned " + result + " calories.");
        return miles;
    }

    // The toString() method returns "This is a bicycle"

    public String toString() {
        return "This is a bicycle";
    }

}