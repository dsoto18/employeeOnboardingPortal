import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Service } from 'src/services/service';
import { State } from 'src/services/state';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css'],
  
})
export class TeamsComponent {
  loading:boolean=true;
  teams:any = [];
  dialogDisplay:string='none';
  isAdmin:boolean = false;

  selectedMember:any;
  addMembers:any = [];
  addMemberID: any = [];
  teamMembers:any= [];

  constructor(private state:State, private formBuilder:FormBuilder, private service:Service, private router:Router){}

  openDialog() { this.dialogDisplay = 'flex' }
  closeDialog() { this.dialogDisplay = 'none' }

  async loadTeams() {
    this.loading = true;
    (await this.service.teams(this.state.companyID$.getValue()))
      .pipe(
        catchError((err:any) => {
          return throwError(err);
        })
      )
      .subscribe(data => {
        this.teams = data;
        this.state.teams$.next(this.teams)
    });
    this.loading = false;
  }
  getDisplayName(member:any):string {
    return member.profile.firstName + ' ' + member.profile.lastName.substring(0,1) + '.';
  }
  
  async ngOnInit(){
    this.isAdmin = this.state.isAdmin$.getValue();
    this.loadTeams();
    this.addMembers=[];
    (await this.service.getUsers(this.state.companyID$.getValue()))
    .pipe(
      catchError((err:any) => {
        this.router.navigate(["/"])
        return throwError(err);
      })
    )
    .subscribe(data => {
      this.teamMembers = data;
    });
    this.loading = false;
  }
  teamForm = this.formBuilder.group({
    name: '',
    description: ''
  })

  select(id:any){
    this.selectedMember = id
  }

  async addMember() {
    (await this.service.getUser(this.state.companyID$.getValue(), this.selectedMember))
      .pipe(catchError((err:any) => {
        console.log(err);
        return throwError(err);})
    ).subscribe((data)=>{
      // console.log(data);
      if (this.addMemberID.indexOf(data.id) <= -1) {
        this.addMembers.push(data);
        this.addMemberID.push(data.id);
      }
    })
  }
  removeMember(member:any){
    let index = this.addMembers.indexOf(member, 0)
    let idIndex = this.addMemberID.indexOf(member.id,0)
    this.addMemberID.splice(idIndex, 1)
    this.addMembers.splice(index, 1)
  }
  
  async onSubmit() {
    console.log(this.teamForm.value)
    let name = this.teamForm.value.name
    let desc = this.teamForm.value.description
    let newTeam = [];
    if (name && desc && this.addMembers.length > 0){
      (await this.service.postTeam(this.state.companyID$.getValue(), name, desc, this.addMembers))
      .pipe(
        catchError((err:any) => {
          alert('Team creation failed')
            return throwError('Test')
        })
      )
      .subscribe(data => {
      newTeam = data;
      this.state.teams$.next(newTeam);
      this.teamForm.reset();
      this.closeDialog();
      this.router.navigate(["/teams"])
      this.ngOnInit()
      return data
    });
    } else {
      alert("Empty Fields")
    }
    this.teamForm.reset();
    this.closeDialog();
    this.router.navigate(["/teams"])
    this.ngOnInit()
  }
}
