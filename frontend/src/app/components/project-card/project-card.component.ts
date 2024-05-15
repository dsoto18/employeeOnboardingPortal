import { Component, Input } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { catchError, throwError } from 'rxjs';
import { Service } from 'src/services/service';
import { State } from 'src/services/state';

@Component({
  selector: 'app-project-card',
  templateUrl: './project-card.component.html',
  styleUrls: ['./project-card.component.css']
})
export class ProjectCardComponent {
  isAdmin:boolean = false;
  displayStatus = 'none';
  // isActive:boolean = true;
  statusStyling:any = {};
  status:string=''
  @Input() data:any = {
    name:'Unnamed Project',
    description:'Empty Description'
  }
  isActiveField:boolean | undefined = undefined;

  constructor(private formBuilder:FormBuilder, private service:Service, private state:State){}

  openModal() {
    this.displayStatus = 'flex';
  }
  closeModal() {
    this.displayStatus = 'none';
  }
  ngOnInit(){
    this.setStyle();
    this.isAdmin = this.state.isAdmin$.getValue();
  }
  setStyle(){
    if (this.data.active) {
      this.status = 'Active'
      this.statusStyling = {
        'color': '#22DD2ABF',
        'font-style':'normal'
      }
    } else {
      this.status = 'Inactive'
      this.statusStyling = {
        'color': '#DF0F0FBF',
        'font-style':'italic'
      }
    }
  }
  select(value:string){
    if(value=='true')this.isActiveField = true;
    if(value=='false')this.isActiveField = false;
  }

  projectForm = this.formBuilder.group({
    name:'',
    description:''
  })
  onSubmit() {
    console.log(this.projectForm.value)
    let name = this.projectForm.value.name
    let desc = this.projectForm.value.description
    //-------NEED TO CHECK FOR WORKER VIEW AND IGNORE ACTIVE--------
    if (name && desc) {
      if (this.isAdmin && this.isActiveField != undefined) {
        this.editProject(name, desc, this.isActiveField)
      } else {
        this.editProject(name,desc,this.data.active)
      }
    } else {
      alert("Empty Fields")
    }
    this.projectForm.reset();
    this.closeModal();
  }
  async editProject(name:string, desc:string, active:boolean) {
    (await this.service.editProjects(this.data.id, name, desc, active))
    .pipe(
      catchError((err:any) => {
        alert("Failed Edit")
        return throwError(err);
      })
    ).subscribe((data)=>{
      alert("Succesfully edited")
      this.data = data;
      this.setStyle();
    })
  }
}
