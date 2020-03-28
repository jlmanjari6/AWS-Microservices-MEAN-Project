import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketGenarationComponent } from './ticket-genaration.component';

describe('TicketGenarationComponent', () => {
  let component: TicketGenarationComponent;
  let fixture: ComponentFixture<TicketGenarationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TicketGenarationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TicketGenarationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
