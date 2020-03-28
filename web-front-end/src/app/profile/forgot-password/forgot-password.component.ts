import { NgForm } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Auth } from 'aws-amplify';
import { Router } from "@angular/router";

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  emailPattern = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
  error: string;

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  handleForgotPassword(emailForm: NgForm): void {

    Auth.forgotPassword(emailForm.value.email).then(data => {      
      this.router.navigate(['/ForgotPasswordVerification']);
    })
      .catch(err => {
        console.log(err);
        this.error = err.message;
      });
  }

}
