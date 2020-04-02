import { NgForm } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Auth } from 'aws-amplify';
import { Router } from "@angular/router";

@Component({
  selector: 'app-forgot-password-verification',
  templateUrl: './forgot-password-verification.component.html',
  styleUrls: ['./forgot-password-verification.component.css']
})
export class ForgotPasswordVerificationComponent implements OnInit {

  error: string;

  emailPattern = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
  otpPattern = /^[0-9]{6}$/;
  passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[\^$*.\[\]{}\(\)?\-“!@#%&/,><\’:;|_~`])\S{6,99}$/;
  pwdLengthExp = new RegExp('^.{6,99}$');
  lowercaseExp = new RegExp('(?=.*[a-z])');
  uppercaseExp = new RegExp('(?=.*[A-Z])');
  numericExp = new RegExp('(?=.*[0-9])');
  specialCharacterExp = new RegExp('(?=.*[\^$*.\[\]{}\(\)?\-“!@#%&/,><\’:;|_~`])');

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  handleForgotPassword(emailForm: NgForm) {
    Auth.forgotPasswordSubmit(emailForm.value.email, emailForm.value.code, emailForm.value.password)
      .then(data => {
        this.router.navigate(['/Login']);
      })
      .catch(err => {
        console.log(err);
        this.error = err.message;
      });
  }

}
