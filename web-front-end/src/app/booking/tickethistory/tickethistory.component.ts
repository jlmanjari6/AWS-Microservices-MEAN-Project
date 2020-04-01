import { Router } from '@angular/router';
import { AuthService } from '../../helpers/services/AuthService';
import { Component, OnInit } from '@angular/core';
import { APIService } from '../../helpers/services/APIService';

@Component({
  selector: 'app-tickethistory',
  templateUrl: './tickethistory.component.html',
  styleUrls: ['./tickethistory.component.css']
})
export class TickethistoryComponent implements OnInit {

  tickets = [];
  error: string;

  constructor(private authService: AuthService, private regSvc: APIService,
    private router: Router) {
    if (authService.getAuthStatus()) {
      this.getTicketsforCurrentUser();
    }
    else {
      this.router.navigate(["/Search"]);
    }
  }

  ngOnInit(): void {
  }

  getTicketsforCurrentUser(): void {
    this.error = "";
    this.regSvc.getTicketsforCurrentUser(this.authService.getUserID()).subscribe(res => {
      for (let i = 0; i < res.length; i++) {
        this.tickets.push(res[i]);
      }
      if (res.length == 0) {
        this.error = "No trips booked!"
      }
    });    
  }
}
