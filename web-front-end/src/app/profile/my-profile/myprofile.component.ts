import { AuthService } from './../../helpers/services/AuthService';
import { Component, OnInit } from '@angular/core';
import { User } from './../../helpers/models/user.model';
import { Auth } from 'aws-amplify';

@Component({
  selector: 'app-myprofile',
  templateUrl: './myprofile.component.html',
  styleUrls: ['./myprofile.component.css']
})
export class MyprofileComponent implements OnInit {

  user: User;
  test = "test";

  constructor(private authService: AuthService) {
    this.user = new User();
    this.user.email = this.authService.getUser();
    this.getUserProfile();
  }

  ngOnInit(): void {

  }

  getUserProfile(): void {
    Auth.currentUserInfo().then(data => {      
      this.user.givenName = data.attributes["given_name"];
      this.user.lastName = data.attributes["family_name"];
      this.user.gender = data.attributes["gender"];
      this.user.phoneNumber = data.attributes["phone_number"];      
      this.user.dateOfBirth = data.attributes["birthdate"];      
    });
  }

}
