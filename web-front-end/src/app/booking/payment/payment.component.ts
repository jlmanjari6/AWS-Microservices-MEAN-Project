import { AuthService } from '../../helpers/services/AuthService';
import { APIService } from '../../helpers/services/APIService';
import { Ticket } from '../../helpers/models/Ticket.model';
import { DataService } from '../../helpers/services/DataService';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from "@angular/router";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  error: string;
  cardNo = new FormControl();
  cardHolderName = new FormControl();
  cvv = new FormControl();
  ticket: Ticket;

  constructor(private router: Router, private authService: AuthService, private dataService: DataService, private registrationSvc: APIService) { }

  ngOnInit(): void {
    if (!this.authService.getAuthStatus()) {
      this.router.navigate(['/Login']);
    }
    if (this.dataService.currentData != undefined) {
      this.dataService.currentData.subscribe(data => this.ticket = data);
    }
  }

  proceedToPayment(): void {

    if (this.cardHolderName.value == "" || this.cardNo.value == "" || this.cvv.value == "") {
      this.error = "All fields are required!"
    }
    else if (this.cardNo.value != "" && this.cardNo.value != "1111-1111-1111-1111") {
      this.error = "Either Card number is invalid or it is not in format XXXX-XXXX-XXXX-XXXX"
    }
    else {
      this.error = ""
      this.ticket.userId = this.authService.getUserID();
      this.saveTicketstoDB();
      this.router.navigate(["/GenerateTicket"]);
    }
  }

  saveTicketstoDB(): void {        
    this.registrationSvc.saveTicketstoDB(this.ticket)
      .subscribe(res => {
        this.ticket.ticketId = res.insertId;
      });
  }

}
