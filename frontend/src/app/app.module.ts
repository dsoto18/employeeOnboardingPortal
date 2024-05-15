import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TeamCardComponent } from './components/team-card/team-card.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './pages/home/home.component';
import { ProjectsComponent } from './pages/projects/projects.component';
import { UsersComponent } from './pages/users/users.component';
import { LoginComponent } from './pages/login/login.component';
import { CompanyComponent } from './pages/company/company.component';
import { AnnouncementComponent } from './components/announcement/announcement.component';
import { TeamsComponent } from './pages/teams/teams.component';
import { LoginCardComponent } from './components/login-card/login-card.component';
import { ProjectCardComponent } from './components/project-card/project-card.component';
import { Service } from 'src/services/service';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
    AppComponent,
    TeamCardComponent,
    NavbarComponent,
    HomeComponent,
    ProjectsComponent,
    UsersComponent,
    LoginComponent,
    CompanyComponent,
    AnnouncementComponent,
    TeamsComponent,
    LoginCardComponent,
    ProjectCardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [Service],
  bootstrap: [AppComponent]
})
export class AppModule { }
