package com.example.tourismapp.Models;

public class BusData {

    public int id, busNo, locationId, fare;
    public String startTime, endTime;

    public BusData(int id, int busNo, int locationId, int fare, String startTime, String endTime) {
        this.id = id;
        this.busNo = busNo;
        this.locationId = locationId;
        this.fare = fare;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getBusId() {
        return id;
    }

    public int getBusNo() {
        return busNo;
    }

    public int getLocationId() {
        return locationId;
    }

    public int getFare() {
        return fare;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public BusData setBusId(int id) {
        this.id = id;
        return this;
    }

    public BusData setBusNo(int busNo) {
        this.busNo = busNo;
        return this;
    }

    public BusData setLocationId(int locationId) {
        this.locationId = locationId;
        return this;
    }

    public BusData setFare(int fare) {
        this.fare = fare;
        return this;
    }

    public BusData setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public BusData setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }
}
