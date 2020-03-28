import { DataService } from './helpers//services/DataService';
import { APIService } from './helpers//services/APIService';
import { AuthService } from './helpers/services/AuthService';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { StorageServiceModule } from 'ngx-webstorage-service';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import { HttpModule, Headers } from '@angular/http';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatRadioModule } from '@angular/material/radio';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';

import { AppComponent } from './app.component';
import { LoginComponent } from './profile/login/login.component';
import { SignupComponent } from './profile/signup/signup.component';
import { WelcomeComponent } from './profile/signup/welcome.component';
import { AnalyticsComponent } from './analytics/analytics.component';
import { SearchComponent } from './search/search.component';
import { ConfirmSigninComponent } from './profile/login/confirm-signin/confirm-signin.component';
import { ForgotPasswordComponent } from './profile/forgot-password/forgot-password.component';
import { ForgotPasswordVerificationComponent } from './profile/forgot-password/forgot-password-verification/forgot-password-verification.component';
import { BookingComponent } from './booking/booking.component';
import { PaymentComponent } from './booking/payment/payment.component';
import { TicketGenarationComponent } from './booking/ticket-generation/ticket-genaration.component';
import { TickethistoryComponent } from './booking/tickethistory/tickethistory.component';
import { MyprofileComponent } from './profile/my-profile/myprofile.component';
import { ChartsModule } from 'ng2-charts';

const appRoutes: Routes = [
  {
    path: "Search",
    component: SearchComponent
  },
  {
    path: "Booking",
    component: BookingComponent
  },
  {
    path: "Analytics",
    component: AnalyticsComponent
  },
  {
    path: "Ticket",
    component: TickethistoryComponent
  },
  {
    path: "Login",
    component: LoginComponent
  },
  {
    path: "SignUp",
    component: SignupComponent
  },
  {
    path: "",
    redirectTo: "/Login",
    pathMatch: "full"
  },
  {
    path: "Welcome",
    component: WelcomeComponent
  },
  {
    path: "ConfirmSignIn",
    component: ConfirmSigninComponent
  },
  {
    path: "ForgotPassword",
    component: ForgotPasswordComponent
  },
  {
    path: "ForgotPasswordVerification",
    component: ForgotPasswordVerificationComponent
  },
  {
    path: "PaymentGateway",
    component: PaymentComponent
  },
  {
    path: "GenerateTicket",
    component: TicketGenarationComponent
  },
  {
    path: "MyProfile",
    component: MyprofileComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    WelcomeComponent,
    AnalyticsComponent,
    SearchComponent,
    ConfirmSigninComponent,
    ForgotPasswordComponent,
    ForgotPasswordVerificationComponent,
    BookingComponent,
    PaymentComponent,
    TicketGenarationComponent,
    TickethistoryComponent,
    MyprofileComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(appRoutes),
    FormsModule,
    StorageServiceModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    HttpModule,
    MatSelectModule,
    MatButtonModule,
    MatRadioModule,
    MatTableModule,
    ChartsModule
  ],
  providers: [AuthService, APIService, DataService],
  bootstrap: [AppComponent]
})
export class AppModule {

}
