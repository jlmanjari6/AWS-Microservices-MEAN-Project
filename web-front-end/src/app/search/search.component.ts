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
  error: string;

  constructor(private authService: AuthService, private router: Router,
    private apiSvc: APIService) { }

  ngOnInit(): void {
  }

  onSearch(value: string) {
    this.keyword = value;
    this.apiSvc.getAttractions(this.keyword).subscribe(res => {
      this.attractions = [];
      for (let i = 0; i < res.length; i++) {
        this.attractions.push(res[i]);
      }

      if (this.attractions.length == 0) {
        this.error = "No matching results found. Please try again!";
        return;
      }
      else {
        this.error = "";
        // increment number of hits 
        let locationId = { locationId: this.attractions[0]["id"] };
        this.apiSvc.incrementNoOfHits(locationId)
          .subscribe(res => { });
      }
    });
  }

  onClickBook(): void {
    // check if a login session exists - yes: navigate to booking page, no: navigate to login page
    if (this.authService.getAuthStatus()) {
      this.router.navigate(["/Booking"]);
    }
    else {
      const navigationExtras: NavigationExtras = { state: { fromComponent: 'search' } };
      this.router.navigate(["/Login"], navigationExtras);
    }
  }

}
