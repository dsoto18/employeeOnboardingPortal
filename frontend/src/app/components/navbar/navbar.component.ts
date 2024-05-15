import { Component } from '@angular/core';
import { State } from 'src/services/state';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  isAdmin:boolean = false;
  username:string=''

  constructor(private state:State) {} 

  ngOnInit():void{
    this.username=this.state.profile$.getValue().firstName + 
      " " + this.state.profile$.getValue().lastName.substring(0,1) + "."
    this.isAdmin=this.state.isAdmin$.getValue();
  }
}

