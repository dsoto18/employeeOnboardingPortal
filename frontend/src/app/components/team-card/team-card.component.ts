import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { Service } from 'src/services/service';
import { State } from 'src/services/state';

@Component({
  selector: 'app-team-card',
  templateUrl: './team-card.component.html',
  styleUrls: ['./team-card.component.css']
})
export class TeamCardComponent {
  @Input() data:any={name:'Team Name'};
  projects:any;
  members:any;
  projectCount:number= 0;
  displayNames:string[] = [];

  constructor(private state:State, private router:Router, private service:Service){}
  async loadProjects(){
    (await this.service.projects(this.data.id, this.state.companyID$.getValue()))
    .pipe(
      catchError((err:any) => {
        return throwError(err);
      })
    ).subscribe((data)=>{
      console.log(data);
      this.projects = data;
      this.projectCount = data.length;
    })
  }
  ngOnInit(){
    console.log(this.data);
    this.members=this.data.teammates;
    for (let member of this.members) {
      let name = member.profile.firstName + ' ' + member.profile.lastName.substring(0,1) + '.';
      this.displayNames.push(name);
    }
    this.loadProjects();
  }
  onClick(){
    // for (var team of this.state.teams$.getValue()){
    //   if (team.name == this.data.name){
    //     this.state.teamID$ = team.id;
    //   }
    // }
    this.state.teamID$.next(this.data.id);
    this.state.projects$.next(this.projects);
    this.router.navigate(["projects"])
  }
}