import { AuthService } from './../../../helpers/services/AuthService';
import { APIService } from './../../../helpers/services/APIService';
import { NgForm } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Auth } from 'aws-amplify';
import { Router, NavigationExtras } from '@angular/router';

@Component({
  selector: 'app-confirm-signin',
  templateUrl: './confirm-signin.component.html',
  styleUrls: ['./confirm-signin.component.css']
})
export class ConfirmSigninComponent implements OnInit {

  code: string;
  cognitoUser: any;
  authService: AuthService;
  error: string;
  fromComponent: string;
  searchedLocationId: string;

  constructor(authService: AuthService, private router: Router, private apiSvc: APIService) {
    this.authService = authService;
    const navigation = this.router.getCurrentNavigation();
    const state = navigation.extras.state as { fromComponent: string, locationId:string };
    this.fromComponent = state.fromComponent;
    this.searchedLocationId = state.locationId;
  }

  ngOnInit(): void {
  }

  confirmLogin(confirmloginForm: NgForm): void {
    this.code = confirmloginForm.value.code;
    this.cognitoUser = this.authService.getCognitoUser();    

    Auth.confirmSignIn(this.cognitoUser, this.code, "SMS_MFA")
      .then(data => {
        console.log(data);
        this.authService.setAuthStatus(true);

        // if we landed here from search page, navigate to booking page, else to search page
        if (this.fromComponent == "search") {
          const navigationExtras: NavigationExtras = { state: { fromComponent: this.fromComponent, locationId: this.searchedLocationId } };
          this.router.navigate(['/Booking'], navigationExtras);
        }
        else {
          this.router.navigate(['/Search']);
        }
      }).catch(err => {
        console.log(err);
        this.error = err.message;
      });

    // get userId from DB
    this.apiSvc.getUserID(this.authService.getUser()).subscribe(res => {
      for (let i = 0; i < res.length; i++) {        
        this.authService.setUserID(res[i]["id"]);        
      }
    });

    // if we landed here from search page, navigate to booking page, else to search page
    if (this.fromComponent == "search") {
      this.router.navigate(['/Booking']);
    }
    else {
      this.router.navigate(['/Search']);
    }
  }


}
