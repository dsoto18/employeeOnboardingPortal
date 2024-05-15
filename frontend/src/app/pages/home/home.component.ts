import { Component, Input } from '@angular/core';
import { State } from 'src/services/state';
import { Form, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Service } from 'src/services/service';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  dialogDisplay:string = 'none';
  isAdmin:boolean = true;
  loading:boolean = true;
  announcements:any = [];
  announcementForm:any = new FormGroup({
    title: new FormControl('',
      [Validators.required]
    ),
    message: new FormControl('',
      [Validators.required]
    )
  });

  constructor(private state:State, private formBuilder:FormBuilder, private service:Service, private router:Router){}

  openDialog() { this.dialogDisplay = 'flex' }
  closeDialog() { this.dialogDisplay = 'none' }

  async loadAnnouncements(){
    this.loading = true;
    (await this.service.getAnnouncements(this.state.companyID$.getValue()))
    .pipe(
      catchError((err:any) => {
          this.router.navigate(["/"])
          return throwError('Test')
      })
    )
    .subscribe(data => {
      this.announcements = data;
      this.loading = false;
    });
  }

  async ngOnInit() {
    this.isAdmin = this.state.isAdmin$.getValue();

    //SQL GET HERE
    this.loadAnnouncements();
  }

  async onSubmit() {
    console.log(this.announcementForm.value)
    // console.log(this.announcementForm.title.status)
    let title = this.announcementForm.value.title
    let message = this.announcementForm.value.message
    if (title && message){
    //SQL POST HERE
      let id = this.state.companyID$.getValue();
      let user = this.state.user$.getValue();
      (await this.service.postAnnouncement(id, title, message, {
        id:user.id,
        profile:user.profile,
        admin:user.admin,
        active:user.active,
        status:user.status
      }))
      .pipe(
        catchError((err:any) => {
          alert('User Not Found')
            return throwError('Test')
        })
      ).subscribe(data =>this.loadAnnouncements());
    } else {
      alert("Empty Fields")
    }
    this.announcementForm.reset();
    this.closeDialog();
  }
}
