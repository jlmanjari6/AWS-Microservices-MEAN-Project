import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ForgotPasswordVerificationComponent } from './forgot-password-verification.component';

describe('ForgotPasswordVerificationComponent', () => {
  let component: ForgotPasswordVerificationComponent;
  let fixture: ComponentFixture<ForgotPasswordVerificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForgotPasswordVerificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ForgotPasswordVerificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
