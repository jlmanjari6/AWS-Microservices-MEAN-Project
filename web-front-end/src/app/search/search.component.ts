import { APIService } from './../helpers/services/APIService';
import { AuthService } from './../helpers/services/AuthService';
import { Component, OnInit } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  keyword: string;
  attractions = [];
  locationIds = [];
  bookingId: number;
  error: string;

  constructor(private authService: AuthService, private router: Router, private apiSvc: APIService) { }

  ngOnInit(): void {
  }

  onSearch(value: string) {
    this.keyword = value;
    this.apiSvc.getAttractions(this.keyword).subscribe(res => {
      this.attractions = [];
      if (res.length > 0) {
        this.error = '';
        for (const location of res) {
          this.attractions.push(location);
          if (!this.locationIds.includes(location.id)) {
            this.locationIds.push(location.id);
          }
        }
        // increment number of hits for each location
        for (const locationId of this.locationIds) {
          this.apiSvc.incrementNoOfHits({ locationId })
            .subscribe(() => { });
        }
        // console.log(this.locationIds);
        // let locationId = { locationId: this.attractions[0]["id"] };
        // this.apiSvc.incrementNoOfHits(locationId)
        //   .subscribe(() => { });
      } else {
        this.error = 'No matching results found. Please try again!';
        return;
      }
    });
  }

  onClickBook(value: number) {
    this.bookingId = value;
    // console.log({ bookingId: this.bookingId });
    // check if a login session exists - yes: navigate to booking page, no: navigate to login page
    if (this.authService.getAuthStatus()) {
      const navigationExtras: NavigationExtras = { state: { fromComponent: 'search', locationId: this.bookingId } };
      this.router.navigate(['/Booking'], navigationExtras);
    } else {
      const navigationExtras: NavigationExtras = { state: { fromComponent: 'search', locationId: this.bookingId } };
      this.router.navigate(['/Login'], navigationExtras);
    }
  }

}
