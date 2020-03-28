import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { map } from 'rxjs/operators';

@Injectable()
export class APIService {
    constructor(private http: Http) {

    }

    // access POST api to save User
    saveUser(newUser) {
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.post("http://localhost:3000/users", JSON.stringify(newUser), { headers: headers })
            .pipe(map(res => res.json()));
    }

    // access POST api to save Ticket details
    saveTicketstoDB(ticket) {
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.post("http://localhost:3000/ticket", JSON.stringify(ticket), { headers: headers })
            .pipe(map(res => res.json()));
    }

    // access GET api to fetch ticket details for a user
    getTicketsforCurrentUser(userId) {
        return this.http.get("http://localhost:3000/tickets/user/" + userId)
            .pipe(map(res => res.json()));
    }

    // access GET api to get userId by email
    getUserID(email) {
        return this.http.get("http://localhost:3000/users/" + email)
            .pipe(map(res => res.json()));
    }

    // access GET api to get attraction details by keyword
    getAttractions(location) {
        return this.http.get("http://localhost:3000/search/" + location)
            .pipe(map(res => res.json()));
    }

    // access GET api to get list of locations
    getLocations() {
        return this.http.get("http://localhost:3000/locations")
            .pipe(map(res => res.json()));
    }

    // access GET api to get list of buses for selected destination
    getBuses(locationId) {
        return this.http.get("http://localhost:3000/locations/" + locationId)
            .pipe(map(res => res.json()));
    }

    // access GET api to increment number of hits
    incrementNoOfHits(locationId) {
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.post("http://localhost:3000/location", JSON.stringify(locationId), { headers: headers })
            .pipe(map(res => res.json()));
    }

    // access GET api to fetch top searched places
    getTopPlaces(){
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.get("http://localhost:3000/topsearchedplaces")
            .pipe(map(res => res.json()));
    }

    // access GET api to fetch number of bookings in past 7 days
    getBookingDetails(){
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.get("http://localhost:3000/noOfBookings")
            .pipe(map(res => res.json()));
    }
}
