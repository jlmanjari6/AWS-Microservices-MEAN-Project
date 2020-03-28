import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from '../../helpers/services/DataService';
import { Ticket } from '../../helpers/models/Ticket.model';
import { AuthService } from '../../helpers/services/AuthService';
import * as jsPDF from 'jspdf';

@Component({
  selector: 'app-ticket-genaration',
  templateUrl: './ticket-genaration.component.html',
  styleUrls: ['./ticket-genaration.component.css']
})
export class TicketGenarationComponent implements OnInit {
  ticket: Ticket;

  @ViewChild('ticketDiv') content: ElementRef;

  constructor(private router: Router, private authService: AuthService, private dataService: DataService) {
    if (!this.authService.getAuthStatus()) {
      this.router.navigate(['/Login']);
    }
    if (this.dataService.currentData != undefined) {
      this.dataService.currentData.subscribe(data => this.ticket = data);
    }
  }

  ngOnInit(): void {
  }

  downloadAsPDF(): void {
    let doc = new jsPDF();
    let specialEleHandlers = {
      '#editor': function (element, renderer) {
        return true;
      }
    };

    let content = this.content.nativeElement;
    doc.fromHTML(content.innerHTML, 10, 10, {
      'width': 190,
      'elementHandlers': specialEleHandlers
    });

    doc.save('Ticket.pdf');

  }
}

