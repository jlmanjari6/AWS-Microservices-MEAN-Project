import { APIService } from './../helpers/services/APIService';
import { AuthService } from '../helpers/services/AuthService';
import { Ticket } from './../helpers/models/Ticket.model';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from "@angular/router";
import { DataService } from "../helpers/services/DataService";

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit {

  minDate = new Date();
  date = new FormControl(new Date());
  selectedFromLocation: string;
  selectedToLocation: any;
  productForm = new FormGroup({ selLocation: new FormControl("") });
  noOfPassengers: string;
  dataSource: any;
  selectedBus: Bus;
  displayedColumns = ['selected', 'Bus No.', 'From-time', 'To-time', 'Total fare'];
  ticket: Ticket;
  error: string;
  isBusAllowed: boolean = false;
  locations: any;
  destinationId = "";
  fromComponent: string;
  searchedLocationId: string;

  constructor(private router: Router, private dataService: DataService, private authService: AuthService,
    private apiSvc: APIService) {
    this.getLocations();
    const navigation = this.router.getCurrentNavigation();
    const state = navigation.extras.state as { fromComponent: string, locationId: string };
    if (state != undefined) {
      this.fromComponent = state.fromComponent;
      this.searchedLocationId = state.locationId;
    }
  }

  ngOnInit(): void {
    if (!this.authService.getAuthStatus()) {
      this.router.navigate(['/Login']);
    }
  }

  getLocations(): void {
    this.apiSvc.getLocations().subscribe(res => {
      this.locations = [];
      for (let i = 0; i < res.length; i++) {
        this.locations.push({ id: res[i]["id"], name: res[i]["name"] });
        if (this.fromComponent == 'search') {
          if (res[i]["id"] == this.searchedLocationId) {
            this.productForm.controls['selLocation'].setValue(res[i]["id"]);
            this.selectedToLocation = { id: res[i]["id"], name: res[i]["name"] };
          }
        }
      }
    });
  }

  findBuses(): void {
    console.log(this.productForm.controls["selLocation"]);
    const temp = (this.productForm.controls["selLocation"]).value;
    if (this.selectedToLocation == undefined) {
      this.selectedToLocation = {};
    }
      for (let i = 0; i < this.locations.length; i++) {
        if (this.locations[i]["id"] == temp) {
          this.selectedToLocation["id"] = temp;
          this.selectedToLocation["name"] = this.locations[i]["name"];
        }
      }
    // if (this.selectedToLocation == undefined) {
    //   this.selectedToLocation = {};
    //   for (let i = 0; i < this.locations.length; i++) {
    //     if (this.locations[i]["id"] == temp) {
    //       this.selectedToLocation["id"] = temp;
    //       this.selectedToLocation["name"] = this.locations[i]["name"];
    //     }
    //   }
    // }
    console.log(this.selectedToLocation);

    if (this.selectedFromLocation == undefined || this.selectedToLocation == undefined
      || !this.date.touched || this.noOfPassengers == undefined) {
      this.error = "All fields are required!"
      this.isBusAllowed = false;
      return;
    }
    if (this.selectedFromLocation["name"] == this.selectedToLocation["name"]) {
      this.error = "Source and destination must not be same!"
      this.isBusAllowed = false;
      return;
    }
    this.isBusAllowed = true;
    this.error = "";

    // get bus data from DB
    this.destinationId = this.selectedToLocation["id"];
    console.log(this.destinationId);
    this.apiSvc.getBuses(parseInt(this.destinationId)).subscribe(res => {
      if (res.length == 0) {
        this.error = "Invalid inputs! Please try again!";
        return;
      }
      const busData: Bus[] = [];
      for (let i = 0; i < res.length; i++) {
        busData.push(
          { busId: res[i].id, busNo: res[i].busNo, fromTime: res[i].startTime, toTime: res[i].endTime, fare: res[i].fare }
        );
      }
      this.dataSource = busData;
    });
  }

  proceedToPayment(): void {
    //validation
    this.error = "";
    if (this.selectedFromLocation == undefined || this.selectedToLocation == undefined
      || !this.date.touched || this.noOfPassengers == undefined ||
      this.selectedBus == undefined) {
      this.error = "All fields are required!"
      return;
    }
    if (this.selectedFromLocation == this.selectedToLocation) {
      this.error = "Source and destination must not be same!"
      return;
    }
    if (this.destinationId != this.selectedToLocation["id"]) {
      this.error = "Invalid inputs! Please try again!";
      return;
    }

    this.error = "";
    //send data to payment gateway component
    this.ticket = new Ticket();
    this.ticket.userId = this.authService.getUserID();
    this.ticket.email = this.authService.getUser();
    this.ticket.origin = this.selectedFromLocation["name"];
    this.ticket.destination = this.selectedToLocation["name"];
    this.ticket.travelDate = this.date.value;
    this.ticket.bookingDate = new Date().toLocaleString();
    this.ticket.fromTime = this.selectedBus.fromTime;
    this.ticket.toTime = this.selectedBus.toTime;
    this.ticket.fare = "200"; // get from search component
    this.ticket.noOfSeats = this.noOfPassengers;
    this.ticket.busNo = this.selectedBus.busNo;
    this.ticket.busId = this.selectedBus.busId;
    this.ticket.fare = this.selectedBus.fare;
    this.dataService.changeMessage(this.ticket);

    // navigate to payment gateway component
    this.router.navigate(["/PaymentGateway"]);
  }

}

export interface Bus {
  busId: string;
  busNo: string;
  fromTime: string;
  toTime: string;
  fare: string;
}