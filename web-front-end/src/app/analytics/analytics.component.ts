import { Component, OnInit } from '@angular/core';
import { APIService } from './../helpers/services/APIService';
import { SingleDataSet, Label, monkeyPatchChartJsLegend, monkeyPatchChartJsTooltip } from 'ng2-charts';
import { ChartOptions, ChartType } from 'chart.js';

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css']
})



export class AnalyticsComponent implements OnInit {

  places=[]
  noOfHits=[]
  destination=[]
  count=[]

  constructor(private regSvc: APIService)
  {
    this.getBookingDetails();
    this.getTopPlaces();


    monkeyPatchChartJsTooltip();
    monkeyPatchChartJsLegend();
  }

  ngOnInit(): void {
  }

  //to fetch top 5 places
  getTopPlaces()
      {
        this.regSvc.getTopPlaces().subscribe(res1 => {
          for (let i = 0; i < res1.length; i++) {
            this.noOfHits.push(res1[i].count);
            this.places.push(res1[i].name);
          } 
        });
      }

    public pieChartOptions: ChartOptions = {
      responsive: true,
    };
  
    public pieChartLabels: Label[] = this.places;
    public pieChartData: SingleDataSet = this.noOfHits;
    public pieChartType: ChartType = 'pie';
    public pieChartLegend = true;
    public pieChartPlugins = [];
    public pieChartColors: Array < any > = [{
      backgroundColor: ['#003f5c', '#58508d', '#bc5090','#ff6361', '#ffa600'],
      borderColor: ['#c6c6c6','#c6c6c6','#c6c6c6','#c6c6c6','#c6c6c6']
   }];

   //to fetch bookings made in last week
  getBookingDetails()
  {
    this.regSvc.getBookingDetails().subscribe(res => {
      for (let i = 0; i < res.length; i++) {
        this.destination.push(res[i].destination);
        this.count.push(res[i].count);
      } 
    });
  }

  public barChartOptions:any = {
    scaleShowVerticalLines: false,
    responsive: true,
    scales: {
      yAxes: [
        {
          ticks: {
            beginAtZero: true
          }
        }
      ]
    }
  };
  
  public mbarChartLabels:string[] = this.destination //top 5 places
  public barChartType:string = 'bar';
  public barChartLegend:boolean = true;//no legend displayed
    
  public barChartColors:Array<any> = [
    { 
        backgroundColor: '#f9d9f8',
        borderColor: 'rgba(77,20,96,1)',
        pointBackgroundColor: 'rgba(77,20,96,1)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#b089b0',
        pointHoverBorderColor: 'rgba(77,20,96,1)'
      }
    ];

  public barChartData:any[] = [
    {data: this.count, label: 'Number of tickets booked'} //number of tickets
]; 

// events
public chartClicked(e:any):void {
        // console.log(e);
      }
    
public chartHovered(e:any):void {
        // console.log(e);
      }
  
}