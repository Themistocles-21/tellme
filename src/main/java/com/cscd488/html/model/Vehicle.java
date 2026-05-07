package com.cscd488.html.model;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vin;
    private String make;
    private String model;
    private String year;
    private String issueZone;
    private String issueSubLocation;
    private String issueLocation;
    private String issueType;
    private String severity;

    @Column(name = "free_form_text", length = 1000)
    private String freeFormText;

    @ManyToOne
    @JoinColumn(name = "customer_email", referencedColumnName = "email")
    private Customer customer;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getIssueZone() { return issueZone; }
    public void setIssueZone(String issueZone) { this.issueZone = issueZone; }

    public String getIssueSubLocation() { return issueSubLocation; }
    public void setIssueSubLocation(String issueSubLocation) { this.issueSubLocation = issueSubLocation; }

    public String getIssueLocation() { return issueLocation; }
    public void setIssueLocation(String issueLocation) { this.issueLocation = issueLocation; }

    public String getIssueType() { return issueType; }
    public void setIssueType(String issueType) { this.issueType = issueType; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getFreeFormText() { return freeFormText; }
    public void setFreeFormText(String freeFormText) { this.freeFormText = freeFormText; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    // Helper methods for filtering options based on selections
    public List<String> getSubLocationsForZone() {
        if (issueZone == null) return new ArrayList<>();

        switch (issueZone) {
            case "front":
                return Arrays.asList("front_left", "front_center", "front_right", "front_whole");
            case "rear":
                return Arrays.asList("rear_left", "rear_center", "rear_right", "rear_whole");
            case "side":
                return Arrays.asList("driver_door", "passenger_door", "both_sides", "side_general");
            case "interior":
                return Arrays.asList("dashboard", "seats", "floor", "headliner", "interior_general");
            case "top":
                return Arrays.asList("roof", "sunroof", "windshield", "rear_window");
            case "bottom":
                return Arrays.asList("under_engine", "under_cabin", "under_trunk", "whole_undercarriage");
            default:
                return new ArrayList<>();
        }
    }

    public String getSubLocationDisplayName() {
        if (issueSubLocation == null) return "";

        switch (issueSubLocation) {
            case "front_left": return "Front Left";
            case "front_center": return "Front Center";
            case "front_right": return "Front Right";
            case "front_whole": return "Whole Front";
            case "rear_left": return "Rear Left";
            case "rear_center": return "Rear Center";
            case "rear_right": return "Rear Right";
            case "rear_whole": return "Whole Rear";
            case "driver_door": return "Driver Door";
            case "passenger_door": return "Passenger Door";
            case "both_sides": return "Both Sides";
            case "side_general": return "General Side Area";
            case "dashboard": return "Dashboard";
            case "seats": return "Seats";
            case "floor": return "Floor";
            case "headliner": return "Headliner";
            case "interior_general": return "General Interior";
            case "roof": return "Roof";
            case "sunroof": return "Sunroof";
            case "windshield": return "Windshield";
            case "rear_window": return "Rear Window";
            case "under_engine": return "Under Engine";
            case "under_cabin": return "Under Cabin";
            case "under_trunk": return "Under Trunk";
            case "whole_undercarriage": return "Whole Undercarriage";
            default: return issueSubLocation;
        }
    }

    public List<String> getSystemsForLocation() {
        if (issueZone == null || issueSubLocation == null) return new ArrayList<>();

        // Map zone + sublocation to possible systems
        if (issueZone.equals("front")) {
            if (issueSubLocation.equals("front_left")) {
                return Arrays.asList("brakes", "suspension", "tires", "electrical");
            } else if (issueSubLocation.equals("front_center")) {
                return Arrays.asList("engine", "cooling", "transmission", "electrical", "hvac");
            } else if (issueSubLocation.equals("front_right")) {
                return Arrays.asList("brakes", "suspension", "tires", "electrical");
            } else if (issueSubLocation.equals("front_whole")) {
                return Arrays.asList("engine", "cooling", "brakes", "suspension", "tires", "electrical", "body");
            }
        }

        if (issueZone.equals("rear")) {
            if (issueSubLocation.equals("rear_left")) {
                return Arrays.asList("brakes", "suspension", "tires", "exhaust");
            } else if (issueSubLocation.equals("rear_center")) {
                return Arrays.asList("exhaust", "fuel", "suspension");
            } else if (issueSubLocation.equals("rear_right")) {
                return Arrays.asList("brakes", "suspension", "tires", "exhaust");
            } else if (issueSubLocation.equals("rear_whole")) {
                return Arrays.asList("brakes", "suspension", "tires", "exhaust", "body");
            }
        }

        if (issueZone.equals("side")) {
            if (issueSubLocation.equals("driver_door") || issueSubLocation.equals("passenger_door")) {
                return Arrays.asList("body", "electrical", "hvac");
            } else if (issueSubLocation.equals("both_sides")) {
                return Arrays.asList("body", "electrical");
            } else if (issueSubLocation.equals("side_general")) {
                return Arrays.asList("body", "suspension", "tires", "electrical");
            }
        }

        if (issueZone.equals("interior")) {
            if (issueSubLocation.equals("dashboard")) {
                return Arrays.asList("electrical", "hvac", "other");
            } else if (issueSubLocation.equals("seats")) {
                return Arrays.asList("other", "electrical");
            } else if (issueSubLocation.equals("floor")) {
                return Arrays.asList("other", "body");
            } else if (issueSubLocation.equals("headliner")) {
                return Arrays.asList("other", "body", "electrical");
            } else if (issueSubLocation.equals("interior_general")) {
                return Arrays.asList("electrical", "hvac", "other");
            }
        }

        if (issueZone.equals("top")) {
            if (issueSubLocation.equals("roof")) {
                return Arrays.asList("body", "other");
            } else if (issueSubLocation.equals("sunroof")) {
                return Arrays.asList("body", "electrical", "other");
            } else if (issueSubLocation.equals("windshield") || issueSubLocation.equals("rear_window")) {
                return Arrays.asList("body");
            }
        }

        if (issueZone.equals("bottom")) {
            if (issueSubLocation.equals("under_engine")) {
                return Arrays.asList("engine", "transmission", "exhaust", "cooling", "fuel");
            } else if (issueSubLocation.equals("under_cabin")) {
                return Arrays.asList("exhaust", "fuel", "other");
            } else if (issueSubLocation.equals("under_trunk")) {
                return Arrays.asList("exhaust", "suspension", "fuel");
            } else if (issueSubLocation.equals("whole_undercarriage")) {
                return Arrays.asList("engine", "transmission", "exhaust", "suspension", "fuel");
            }
        }

        return Arrays.asList("engine", "transmission", "brakes", "suspension", "electrical", "exhaust", "hvac", "body", "tires", "fuel", "cooling", "other");
    }

    public String getSystemDisplayName(String system) {
        switch (system) {
            case "engine": return "Engine";
            case "transmission": return "Transmission";
            case "brakes": return "Brakes";
            case "suspension": return "Suspension & Steering";
            case "electrical": return "Electrical System";
            case "exhaust": return "Exhaust System";
            case "hvac": return "Heating & Air Conditioning";
            case "body": return "Body / Paint / Glass";
            case "tires": return "Tires & Wheels";
            case "fuel": return "Fuel System";
            case "cooling": return "Cooling System";
            case "other": return "Other";
            default: return system;
        }
    }

    public List<String> getIssueTypesForSystem() {
        if (issueLocation == null) return new ArrayList<>();

        switch (issueLocation) {
            case "engine":
                return Arrays.asList("no_start", "check_engine", "rough_idle", "loss_power", "knocking", "overheating", "oil_leak", "smoke");
            case "transmission":
                return Arrays.asList("slipping", "rough_shifting", "delayed_engagement", "leaking", "noise");
            case "brakes":
                return Arrays.asList("squeaking", "grinding", "soft_pedal", "vibration", "pulling");
            case "suspension":
                return Arrays.asList("bumpy", "noise", "steering", "pulling", "uneven");
            case "electrical":
                return Arrays.asList("battery", "alternator", "lights", "windows", "sensors");
            case "exhaust":
                return Arrays.asList("loud", "smoke", "smell", "rust");
            case "hvac":
                return Arrays.asList("no_heat", "no_ac", "fan", "smell");
            case "body":
                return Arrays.asList("dent", "lights", "mirror", "windshield");
            case "tires":
                return Arrays.asList("flat", "vibration", "uneven_wear", "noise");
            case "fuel":
                return Arrays.asList("no_fuel", "smell", "leak", "mpg");
            case "cooling":
                return Arrays.asList("overheating", "leak", "no_heat", "fan");
            case "other":
                return Arrays.asList("other");
            default:
                return new ArrayList<>();
        }
    }

    public String getIssueTypeDisplayName(String type) {
        switch (type) {
            // Engine
            case "no_start": return "Won't start / No power";
            case "check_engine": return "Check engine light on";
            case "rough_idle": return "Rough idle / Stalling";
            case "loss_power": return "Loss of power while driving";
            case "knocking": return "Knocking or unusual noise";
            case "overheating": return "Engine overheating";
            case "oil_leak": return "Oil leak";
            case "smoke": return "Smoke from exhaust";
            // Transmission
            case "slipping": return "Slipping gears";
            case "rough_shifting": return "Rough shifting";
            case "delayed_engagement": return "Delayed engagement when shifting";
            case "leaking": return "Fluid leak";
            case "noise": return "Unusual noise";
            // Brakes
            case "squeaking": return "Squeaking noise when braking";
            case "grinding": return "Grinding noise when braking";
            case "soft_pedal": return "Soft or spongy brake pedal";
            case "vibration": return "Vibration when braking";
            case "pulling": return "Vehicle pulls to one side when braking";
            // Suspension
            case "bumpy": return "Rough or bumpy ride";
            case "steering": return "Steering wheel vibration";
            case "uneven": return "Uneven tire wear";
            // Electrical
            case "battery": return "Battery not holding charge";
            case "alternator": return "Alternator issue";
            case "lights": return "Lights not working";
            case "windows": return "Power windows not working";
            case "sensors": return "Sensor malfunction";
            // Exhaust
            case "loud": return "Loud exhaust noise";
            case "smell": return "Gas or exhaust smell";
            case "rust": return "Rust or hole in exhaust";
            // HVAC
            case "no_heat": return "No heat from vents";
            case "no_ac": return "No cold air / AC not working";
            case "fan": return "Fan not working";
            // Body
            case "dent": return "Dent or scratch";
            case "mirror": return "Mirror damaged";
            // Tires
            case "flat": return "Flat tire or low pressure";
            case "uneven_wear": return "Uneven tire wear";
            // Fuel
            case "no_fuel": return "Car won't get fuel";
            case "mpg": return "Poor fuel economy";
            // Cooling
            case "coolant_leak": return "Coolant leak";
            default:
                return type.replace("_", " ");
        }
    }
    public String getSubLocationDisplayName(String subLoc) {
        switch (subLoc) {
            case "front_left": return "Front Left";
            case "front_center": return "Front Center";
            case "front_right": return "Front Right";
            case "front_whole": return "Whole Front";
            case "rear_left": return "Rear Left";
            case "rear_center": return "Rear Center";
            case "rear_right": return "Rear Right";
            case "rear_whole": return "Whole Rear";
            case "driver_door": return "Driver Door";
            case "passenger_door": return "Passenger Door";
            case "both_sides": return "Both Sides";
            case "side_general": return "General Side Area";
            case "dashboard": return "Dashboard";
            case "seats": return "Seats";
            case "floor": return "Floor";
            case "headliner": return "Headliner";
            case "interior_general": return "General Interior";
            case "roof": return "Roof";
            case "sunroof": return "Sunroof";
            case "windshield": return "Windshield";
            case "rear_window": return "Rear Window";
            case "under_engine": return "Under Engine";
            case "under_cabin": return "Under Cabin";
            case "under_trunk": return "Under Trunk";
            case "whole_undercarriage": return "Whole Undercarriage";
            default: return subLoc;
        }
    }
}