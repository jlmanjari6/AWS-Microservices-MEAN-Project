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
