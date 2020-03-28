import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TickethistoryComponent } from './tickethistory.component';

describe('TickethistoryComponent', () => {
  let component: TickethistoryComponent;
  let fixture: ComponentFixture<TickethistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TickethistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TickethistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
