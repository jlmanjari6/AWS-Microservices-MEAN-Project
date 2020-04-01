package com.example.tourismapp.Interface;

import com.example.tourismapp.Models.BookingData;
import com.example.tourismapp.Models.BusData;
import com.example.tourismapp.Models.LocationModel;
import com.example.tourismapp.Models.NoOfBooking;
import com.example.tourismapp.Models.TicketDetails;
import com.example.tourismapp.Models.Topplaces;
import com.example.tourismapp.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitApiInterface {
    @POST("users")
    Call<User> createUser(@Body User user);

    @POST("ticket")
    Call<BookingData> createBookingDetails(@Body BookingData bData);

    @GET("users/{email}")
    Call<Object> getUserId(@Path("email") String email);

    @GET("topsearchedplaces")
    Call<List<Topplaces>> topPlaces();

    @GET("noOfBookings")
    Call<List<NoOfBooking>> bookingDetails();

    @GET("booking/tickets/user/{userId}")
    Call<List<TicketDetails>> ticketHistory(@Path("userId") int id);

    @GET("booking/locations")
    Call<List<LocationModel>> locationName();

    @GET("booking/buses/{locationId}")
    Call<List<BusData>> busData(@Path("locationId") int id);
}
