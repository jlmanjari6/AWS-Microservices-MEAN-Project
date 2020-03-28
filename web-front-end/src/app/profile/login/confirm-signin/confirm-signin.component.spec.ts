import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmSigninComponent } from './confirm-signin.component';

describe('ConfirmSigninComponent', () => {
  let component: ConfirmSigninComponent;
  let fixture: ComponentFixture<ConfirmSigninComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmSigninComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmSigninComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
