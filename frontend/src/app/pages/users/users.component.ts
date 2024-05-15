import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError } from 'rxjs';
import { Service } from 'src/services/service';
import { State } from 'src/services/state';
import { Observable, throwError } from 'rxjs';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent {
  users:any;
  makeAdmin:boolean | undefined = undefined;

  constructor(private state:State, private formBuilder:FormBuilder, private router:Router, private service:Service){}
  displayStatus = 'none';

  openModal() {
    this.displayStatus = 'flex';
  }

  closeModal() {
    this.displayStatus = 'none';
  }

  newUser = this.formBuilder.group({
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    password: '',
    confirmPassword: ''
  });

  selectAdmin(value:any){
    if (value=='true') this.makeAdmin = true;
    if (value=='false') this.makeAdmin = false;
  }
  // users = [
  //   {
  //     name: 'Chris Purnell',
  //     email: 'yocrizzle@gmail.com',
  //     activeStatus: 'YES',
  //     adminStatus: 'YES',
  //     status: 'JOINED'
  //   },
  //   {
  //     name: 'Kenny Worth',
  //     email: 'kmoney@gmail.com',
  //     activeStatus: 'YES',
  //     adminStatus: 'YES',
  //     status: 'JOINED'
  //   },
  //   {
  //     name: 'Will Marttala',
  //     email: 'wamizzle@gmail.com',
  //     activeStatus: 'NO',
  //     adminStatus: 'NO',
  //     status: 'PENDING'
  //   },
  //   {
  //     name: 'Helena Makendengue',
  //     email: 'hmasizzle@gmail.com',
  //     activeStatus: 'NO',
  //     adminStatus: 'NO',
  //     status: 'PENDING'
  //   }
  // ];

  async loadUsers(){
    (await this.service.getUsers(this.state.companyID$.getValue()))
    .pipe(
      catchError((err:any) => {
        this.router.navigate(["/"])
        return throwError(err);
      })
    )
    .subscribe(data => {
      console.log(data);
      this.users = data;
    });
  }
  async ngOnInit() {
    this.loadUsers();
  }
  
  async addUser() {
    //event.preventDefault();
    const { firstName, lastName, email, phone, password, confirmPassword } = this.newUser.value;

      
    if (password !== confirmPassword) {
      alert('Passwords do not match');
      return;
    }
    if (firstName&&lastName&&email&&phone&&password&&this.makeAdmin!=undefined) {
      if (firstName)
        (await this.service.newUser(this.state.companyID$.getValue(), firstName, lastName, email, phone, password, this.makeAdmin))
          .pipe(
            catchError((err:any) => {
              alert('Error adding users');
              return throwError(err);
            })
          )
          .subscribe(data => {
            this.loadUsers();
            this.closeModal();
      });
      console.log(this.users);
    } else {
      alert('Missing fields')
    }
  }

}
