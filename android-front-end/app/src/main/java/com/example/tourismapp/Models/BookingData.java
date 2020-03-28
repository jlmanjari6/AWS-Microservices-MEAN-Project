package com.example.tourismapp.Models;

public class BookingData {
    private String origin;
    private String destination;
    private String travelDate;
    private String bookingDate;
    private int userId;
    private int noOfSeats;
    private int busId;

    public BookingData(int userId, String origin, String destination, String travelDate, String bookingDate, int noOfSeats, int busId) {
        this.origin = origin;
        this.destination = destination;
        this.travelDate = travelDate;
        this.bookingDate = bookingDate;
        this.userId = userId;
        this.noOfSeats = noOfSeats;
        this.busId = busId;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public int getUserId() {
        return userId;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public int getBusId() {
        return busId;
    }
}
