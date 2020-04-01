import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { map } from 'rxjs/operators';

@Injectable()
export class APIService {
    
    baseUrl = "https://fv2z97pt9c.execute-api.us-east-1.amazonaws.com/dev";

    constructor(private http: Http) {

    }

    // *************************** PROFILE ****************************************************

    // access POST api to save User
    saveUser(newUser) {
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');        
        const url = this.baseUrl + "/profile/users";
        return this.http.post(url, JSON.stringify(newUser), { headers: headers })
            .pipe(map(res => res.json()));
    }

    // access GET api to get userId by email
    getUserID(email) {        
        const url = this.baseUrl + "/profile/users/" + email;
        return this.http.get(url)
            .pipe(map(res => res.json()));
    }

    // *************************** SEARCH ****************************************************

    // access GET api to get attraction details by keyword
    getAttractions(location) {        
        const url = this.baseUrl + "/search/locations/" + location;
        return this.http.get(url)
            .pipe(map(res => res.json()));
    }    

    // *************************** ANALYTICS ****************************************************

     // access GET api to fetch top searched places
     getTopPlaces() {
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');        
        const url = this.baseUrl +  "/analytics/topsearchedplaces";
        return this.http.get(url)
            .pipe(map(res => res.json()));
    }
   
    // access GET api to increment number of hits
    incrementNoOfHits(locationId) {
        var headers = new Headers();  
        headers.append('Content-Type', 'application/json');        
        const url = this.baseUrl +  "/analytics/location";
        return this.http.post(url, JSON.stringify(locationId), { headers: headers })
            .pipe(map(res => res.json()));
    }

    // *************************** BOOKING ****************************************************

    // access POST api to save Ticket details
    saveTicketstoDB(ticket) {
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');        
        const url = this.baseUrl +  "/booking/ticket";
        return this.http.post(url, JSON.stringify(ticket), { headers: headers })
            .pipe(map(res => res.json()));
    }

    // access GET api to fetch ticket details for a user
    getTicketsforCurrentUser(userId) {        
        const url = this.baseUrl +  "/booking/tickets/user/" + userId;
        return this.http.get(url)
            .pipe(map(res => res.json()));
    }

    // access GET api to get list of locations
    getLocations() {        
        const url = this.baseUrl +  "/booking/locations";
        return this.http.get(url)
            .pipe(map(res => res.json()));
    }

    // access GET api to get list of buses for selected destination
    getBuses(locationId) {        
        const url = this.baseUrl +   "/booking/buses/" + locationId;
        return this.http.get(url)
            .pipe(map(res => res.json()));  
    }

     // access GET api to fetch number of bookings in past 7 days
     getBookingDetails() {
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');        
        const url = this.baseUrl +  "/booking/noOfBookings";
        return this.http.get(url)
            .pipe(map(res => res.json()));
    }

}
