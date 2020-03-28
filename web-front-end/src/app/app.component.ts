import { AuthService } from './helpers/services/AuthService';
import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { Auth } from 'aws-amplify';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Team7';
  authService: AuthService;

  constructor(private router: Router, private _authService: AuthService) {
    this.authService = _authService;
  }

  logout(): void {
    Auth.signOut().then(data => {      
      this.authService.setAuthStatus(false);
      this.authService.setUser(null);
      this.router.navigate(['/Login']);
    });
  }
}
