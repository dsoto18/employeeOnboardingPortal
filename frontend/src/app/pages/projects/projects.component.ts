import { Component } from '@angular/core';
import { State } from 'src/services/state';
import { Form, FormBuilder } from '@angular/forms';
import { Service } from 'src/services/service';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';


@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent {
  displayStatus = 'none';
  isAdmin:boolean = false;
  projects:any = []

  constructor(private state:State, private formBuilder:FormBuilder, private service:Service, private router:Router){}

  ngOnInit() {
    this.isAdmin = this.state.isAdmin$.getValue();
    this.loadProjects();
  }
  openModal() {
    this.displayStatus = 'flex'; //Changed from block to flex for my modal
  }
  closeModal() {
    this.displayStatus = 'none';
  }

  projectForm = this.formBuilder.group({
    name:'',
    description:''
  })
  async onSubmit() {
    console.log(this.projectForm.value)
    let name = this.projectForm.value.name
    let desc = this.projectForm.value.description
    if (name && desc){
    //SQL POST HERE
      let id = this.state.teamID$.getValue();
      (await this.service.postProject(id, name, desc)).pipe(
        catchError((err:any) => {
          alert('Failed to Post')
          return throwError(err);
        }))
      .subscribe(data =>{
        alert("Posted")
        this.projects = this.state.projects$.getValue();
        this.loadProjects();
      });
    } else {
      alert("Empty Fields")
    }
    this.projectForm.reset();
    this.closeModal();
  }
  
  async loadProjects(){
    (await this.service.projects(this.state.teamID$.getValue(), this.state.companyID$.getValue()))
    .pipe(
      catchError((err:any) => {
        this.router.navigate(["/"])
        return throwError(err);
      })
    ).subscribe((data)=>{
      this.projects = data;
    })
  }
}
