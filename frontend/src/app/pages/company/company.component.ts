import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { State } from 'src/services/state';

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.css']
})
export class CompanyComponent {
  selectedCompany:any = undefined;
  companies:any;
  constructor(private state:State, private router:Router){}
  select(value:any){
    this.selectedCompany = value
  }
  ngOnInit() {
    this.companies=this.state.companies$.getValue();
    if (!this.companies) {
      this.router.navigate(["/"])
    }
  }

  chooseCompany(){
    // console.log(this.selectedCompany)
    if (this.selectedCompany != undefined) {
      this.state.companyID$.next(this.selectedCompany);
      this.router.navigate(["/home"])
    }
    // for (var company of this.companies)
    //   if (this.selectedCompany = company.name){
    //     this.state.companyID$.next(company.id)
    //     this.router.navigate(["/home"])
    //   }
    //   else {
    //     alert("No company selected")
    //   }
  } 
}
