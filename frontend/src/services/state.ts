import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Injectable({
    providedIn:"root"
})

export class State {
    isAdmin$ = new BehaviorSubject<boolean>(true); //CHANGE DEFAULT TO SEE WORKER/ADMIN VIEW
    user$ = new BehaviorSubject<any>(null);
    profile$ = new BehaviorSubject<any>(null);
    companies$= new BehaviorSubject<any>(null);
    companyID$ = new BehaviorSubject<number>(0);
    teams$ = new BehaviorSubject<any>(null);
    teamID$ = new BehaviorSubject<number>(0);
    projects$ = new BehaviorSubject<any>(null);
}