import { Ticket } from '../models/Ticket.model';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class DataService {

    private dataSource = new BehaviorSubject(new Ticket);
    currentData = this.dataSource.asObservable();

    constructor() { }

    changeMessage(ticket: Ticket) {
        this.dataSource.next(ticket)
    }

}