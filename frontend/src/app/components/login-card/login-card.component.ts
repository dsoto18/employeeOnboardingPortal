
import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Service } from 'src/services/service';
import { State } from 'src/services/state';
import { Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';


@Component({
  selector: 'app-login-card',
  templateUrl: './login-card.component.html',
  styleUrls: ['./login-card.component.css']
})
export class LoginCardComponent {
  constructor(private state:State, private formBuilder:FormBuilder, private router:Router, private service:Service){}
  
  loginForm = this.formBuilder.group({
    email:'',
    pass:''
  })
  async onSubmit() {
    console.log(this.loginForm.value)
    let email = this.loginForm.value.email
    let pass = this.loginForm.value.pass
    if (email && pass){
    //SQL POST HERE
    //admin
      (await this.service.login(email,pass))
      .pipe(
        catchError((err:any) => {
          alert('User Not Found')
            return throwError('Test')
        })
      )
      .subscribe(data => {
        if (data.admin) {
          this.state.companies$.next(data.companies)
          this.router.navigate(["/company"])
        } else {
          this.state.companyID$.next(data.companies[0].id)
          this.router.navigate(["/home"])
        }
        this.state.isAdmin$.next(data.admin);
        this.state.profile$.next(data.profile)
        this.state.user$.next(data);
      });


      
      // if (false){
      // this.router.navigate(["/home"])
      // } 
      // else{
      //   this.router.navigate(["/company"])
      // }
    }
    else {
      alert("Empty Fields")
    }
    this.loginForm.reset();

  }
}


