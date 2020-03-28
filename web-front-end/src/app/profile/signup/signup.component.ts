import { AuthService } from './../../helpers/services/AuthService';
import { APIService } from './../../helpers/services/APIService';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Auth } from 'aws-amplify';
import { Router } from "@angular/router";
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-signup-component',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  phonePattern = "^(\\+1)[0-9]{10}$";
  emailPattern = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
  maxDate = new Date();
  minDate = new Date(new Date().getFullYear() - 100, 0, 1);
  date = new FormControl(new Date());
  age: number;
  error: string;

  constructor(private router: Router, private authService: AuthService, private registrationSvc: APIService) { }

  ngOnInit(): void {
    if (this.authService.getAuthStatus()) {
      this.router.navigate(['/Search']);
    }
    else {
      this.router.navigate(['/SignUp']);
    }
  }

  // On click of Register button in SignUp page
  register(signupForm: NgForm): void {

    this.age = this.calculateAge(this.date.value); // to calculate age

    // to set up value for birthdate in cognito
    var birthDate = new Date(this.date.value);
    var mm = (birthDate.getMonth() + 1).toString();
    var dd = birthDate.getDate().toString();
    if (mm.length != 2) {
      mm = "0" + mm;
    }
    if (dd.length != 2) {
      dd = "0" + dd;
    }

    let dob = mm + "/" + dd + "/" + birthDate.getFullYear();    

    Auth.signUp({
      username: signupForm.value.email,
      password: signupForm.value.password,

      attributes: {
        email: signupForm.value.email,
        given_name: signupForm.value.givenName,
        family_name: signupForm.value.lastName,
        gender: signupForm.value.gender,
        phone_number: signupForm.value.phone,
        birthdate: dob
      }
    })
      .then(data => {
        //save user rest api call 
        var newUser = {
          firstName: signupForm.value.givenName,
          lastName: signupForm.value.lastName,
          email: signupForm.value.email,
          password: "",
          age: this.age,
          gender: signupForm.value.gender
        }
        this.saveUser(newUser);

        //navigate to welcome page
        this.router.navigate(['/Welcome']);
      })
      .catch(err => {
        console.log(err);
        this.error = err.message;
      });
  }

  // method to calculate the age by DOB
  calculateAge(dob: any): number {
    const today = new Date();
    const birthDate = new Date(dob);
    let result = today.getFullYear() - birthDate.getFullYear();
    const m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
      result--;
    }
    return result;
  }

  // method to call save user rest api call 
  saveUser(newUser) {
    this.registrationSvc.saveUser(newUser)
      .subscribe(user => {
        newUser._id = user.insertedId;
      });
  }

}


