import { Inject, Injectable } from '@angular/core';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';

@Injectable()
export class AuthService {
    constructor(@Inject(LOCAL_STORAGE) private storage: StorageService) {

    }
    private state = {
        isAuthenticated: this.storage.get("isAuthenticated") || false,
        user: this.storage.get("user") || null,
        userID: this.storage.get("userID") || null,
        cognitoUser : this.storage.get("cognitoUser") || null,
        bookingLocation: this.storage.get("bookingLocation") || null        
    };    

    setAuthStatus(isAuthenticated: boolean): void {
        this.state.isAuthenticated = isAuthenticated;
        this.storage.set("isAuthenticated", isAuthenticated);
    }

    setUser(user: string): void {
        this.state.user = user;
        this.storage.set("user", user);
    }

    setUserID(userId: string): void {
        this.state.userID = userId;
        this.storage.set("userID", userId);
    }

    getAuthStatus(): boolean {
        return this.state.isAuthenticated;
    }

    getUser(): string {
        return this.state.user;
    }

    getUserID(): string {
        return this.state.userID;
    }

    getCognitoUser() : any {
        return this.state.cognitoUser;
    }

    setCognitoUser(cognitoUser : any) : void {
        this.state.cognitoUser = cognitoUser;
    }

    setBookingLocation(location : string) : void {
        this.state.bookingLocation = location;
    }

    getBookingLocation(): string {
        return this.state.bookingLocation;
    }
}

//https://www.js-tutorials.com/javascript-tutorial/use-localstorage-sessionstorage-using-webstorage-angular4/
//https://medium.com/@tiagovalverde/how-to-save-your-app-state-in-localstorage-with-angular-ce3f49362e31