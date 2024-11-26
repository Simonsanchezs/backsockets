package com.UdeA.LabWebsockets;

public class FlightInfo {
    private String flightCode;
    private double latitude;
    private double longitude;
    private double course;
    private double speed;
    private double altitude;

    // Getters y Setters
    public String getFlightCode() { return flightCode; }
    public void setFlightCode(String flightCode) { this.flightCode = flightCode; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getCourse() { return course; }
    public void setCourse(double course) { this.course = course; }

    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    public double getAltitude() { return altitude; }
    public void setAltitude(double altitude) { this.altitude = altitude; }
}

