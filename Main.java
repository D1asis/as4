import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Observer Pattern

interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String flightInfo, String weatherInfo, int altitude);
}

class AirTrafficControl implements Subject {
    private List<Observer> observers = new ArrayList<>();

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String flightInfo, String weatherInfo, int altitude) {
        for (Observer observer : observers) {
            observer.update(flightInfo, weatherInfo, altitude);
        }
    }

    public void newFlightArrival(String flightInfo, String weatherInfo, int altitude) {
        System.out.println("New Flight: " + flightInfo);
        System.out.println("Weather Info: " + weatherInfo);
        System.out.println("Initial Altitude: " + altitude + " feet");
        notifyObservers(flightInfo, weatherInfo, altitude);
    }
}

interface Observer {
    void update(String flightInfo, String weatherInfo, int altitude);
}

class AirTrafficController implements Observer {
    private String name;

    public AirTrafficController(String name) {
        this.name = name;
    }

    public void update(String flightInfo, String weatherInfo, int altitude) {
        System.out.println(name + " received Flight Info: " + flightInfo);
        System.out.println(name + " received Weather Info: " + weatherInfo);
        System.out.println(name + " is evaluating the situation...");

        if (altitude > 30000) {
            System.out.println(name + " instructs the aircraft to descend to a safer altitude.");
        } else {
            System.out.println(name + " provides clearance for the current altitude.");
        }
    }
}

// Factory Pattern

interface Aircraft {
    void takeoff();
    void land();
}

class PassengerAircraft implements Aircraft {
    private String name;

    public PassengerAircraft(String name) {
        this.name = name;
    }

    public void takeoff() {
        System.out.println("Passenger Aircraft " + name + " is taking off.");
    }

    public void land() {
        System.out.println("Passenger Aircraft " + name + " is landing.");
    }
}

class CargoAircraft implements Aircraft {
    private String name;

    public CargoAircraft(String name) {
        this.name = name;
    }

    public void takeoff() {
        System.out.println("Cargo Aircraft " + name + " is taking off.");
    }

    public void land() {
        System.out.println("Cargo Aircraft " + name + " is landing.");
    }
}

class AircraftFactory {
    public Aircraft createAircraft(String aircraftType, String name) {
        if (aircraftType.equals("Passenger")) {
            return new PassengerAircraft(name);
        } else if (aircraftType.equals("Cargo")) {
            return new CargoAircraft(name);
        } else {
            return null;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        AirTrafficControl atc = new AirTrafficControl();

        AirTrafficController controller1 = new AirTrafficController("Controller 1");
        AirTrafficController controller2 = new AirTrafficController("Controller 2");

        atc.registerObserver(controller1);
        atc.registerObserver(controller2);

        System.out.println("Select aircraft type: (1) Passenger or (2) Cargo");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        AircraftFactory aircraftFactory = new AircraftFactory();
        Aircraft aircraft;

        if (choice == 1) {
            aircraft = aircraftFactory.createAircraft("Passenger", "Boeing 747");
        } else if (choice == 2) {
            aircraft = aircraftFactory.createAircraft("Cargo", "Airbus A380");
        } else {
            System.out.println("Invalid choice. Exiting...");
            return;
        }

        atc.newFlightArrival("Flight ABC123", "Clear skies", 35000);
        aircraft.takeoff();
        atc.newFlightArrival("Flight XYZ456", "Heavy rain", 28000);
        aircraft.land();
    }
}
