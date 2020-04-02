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

  cardNoRegex = new RegExp('^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\d{3})\d{11})$');
  nameRegex = new RegExp('^[a-zA-Z]+(([\',. -][a-zA-Z ])?[a-zA-Z]*)*$');
  cvvRegex = new RegExp('^[0-9]{3}$');

  constructor(
    private router: Router,
    private authService: AuthService,
    private dataService: DataService,
    private registrationSvc: APIService) { }

  ngOnInit(): void {
    if (!this.authService.getAuthStatus()) {
      this.router.navigate(['/Login']);
    }
    if (this.dataService.currentData !== undefined) {
      this.dataService.currentData.subscribe(data => this.ticket = data);
    }
  }

  proceedToPayment(): void {
    this.error = '';
    if (this.cardHolderName.value === '' || this.cardNo.value === '' || this.cvv.value === '') {
      this.error = 'All fields are required!\n';
    }
    if (this.cardNo.value !== '' && !this.cardNoRegex.test(this.cardNo.value)) {
      this.error += 'Invalid Card Number\n';
    }
    if (this.cardHolderName.value !== '' && !this.nameRegex.test(this.cardHolderName.value)) {
      this.error += 'Invalid Name\n';
    }
    if (this.cvv.value !== '' && !this.cvvRegex.test(this.cvv.value)) {
      this.error += 'Invalid CVV';
    }
    if (this.error === '') {
      this.ticket.userId = this.authService.getUserID();
      this.saveTicketstoDB();
      this.router.navigate(['/GenerateTicket']);
    }
  }

  saveTicketstoDB(): void {
    this.registrationSvc.saveTicketstoDB(this.ticket)
      .subscribe(res => {
        this.ticket.ticketId = res.insertId;
      });
  }

}
