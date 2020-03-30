import { AuthService } from '../../helpers/services/AuthService';
import { Component, OnInit } from '@angular/core';
import { Auth } from 'aws-amplify';
import { NgForm } from '@angular/forms';
import { Router, NavigationExtras } from "@angular/router";

@Component({
  selector: 'app-login-component',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string;
  password: string;
  authService: AuthService;
  error: string;
  fromComponent: string;
  searchedLocationId: string;

  constructor(private router: Router, authService: AuthService) {
    this.authService = authService;

    // check from where we landed to login page - from search page?
    const navigation = this.router.getCurrentNavigation();
    const state = navigation.extras.state as { fromComponent: string, locationId: string };
    if (state != undefined) {
      this.fromComponent = state.fromComponent;
      this.searchedLocationId = state.locationId;
    }
  }

  ngOnInit(): void {
    if (!this.authService.getAuthStatus()) {
      this.router.navigate(['/Login']);
    }
    else {
      this.router.navigate(['/Search']);
    }
  }

  // method to perform login with credentials
  login(loginForm: NgForm) {
    this.email = loginForm.value.email;
    this.authService.setUser(this.email);
    Auth.signIn(loginForm.value.email, loginForm.value.password).then(data => {
      this.authService.setCognitoUser(data); //to save the user data

      // pass the page source info to ConfirmSignIn page
      const navigationExtras: NavigationExtras = { state: { fromComponent: this.fromComponent, locationId: this.searchedLocationId } };
      this.router.navigate(['/ConfirmSignIn'], navigationExtras);
    })
      .catch(err => {
        console.log(err);
        this.error = err.message;
      });
  }
}