import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './pages/home/home.component';
import { ProjectsComponent } from './pages/projects/projects.component';
import { UsersComponent } from './pages/users/users.component';
import { LoginComponent } from './pages/login/login.component';
import { CompanyComponent } from './pages/company/company.component';
import { TeamsComponent } from './pages/teams/teams.component';

const routes: Routes = [
  {path:"", component: LoginComponent},
  {path:"company", component:CompanyComponent},
  {path:"home", component:HomeComponent},
  {path:"teams", component:TeamsComponent},
  {path:"projects", component:ProjectsComponent},
  {path:"users", component:UsersComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
