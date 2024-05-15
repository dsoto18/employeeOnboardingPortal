import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

const url = "http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})
export class Service {

  constructor(private http: HttpClient) { }

  async login(email: string, password: string): Promise<Observable<any>> {
    let data = await this.http.post(url + 'users/login',
      {
        email: email,
        password: password
      })
    return data;
  }


  async getAnnouncements(companyID: number) {
    return await this.http.get(url + 'company/' + companyID + '/announcements');
  }
  async postAnnouncement(companyID: number, title: string, message: string, user: any) {
    return await this.http.post(url + 'announcements/' + companyID, {
      title: title,
      message: message,
      author: user
    })
  }
  async projects(teamID: number, companyID: number): Promise<Observable<any>> {
    let data = await this.http.get(url + 'company/' + companyID + '/teams/' + teamID + '/projects')
    return data;
  }

  async teams(companyID: number):Promise<Observable<any>> {
    return await this.http.get(url + 'company/' + companyID + '/teams')
  }
  async getUsers(companyID:number):Promise<Observable<any>> {
    let data = await this.http.get(url + 'company/'+companyID+'/users')
    return data;
  }
  async getUser(companyID:number, userID:number):Promise<Observable<any>> {
    let data = await this.http.get(url + 'company/'+companyID+'/users/' + userID)
    return data;
  }
  async users(): Promise<Observable<any>> {
    let data = await this.http.get(url + 'users')
    return data;
  }

  async newUser(companyID: number, firstName: string, lastName: string, email: string, phone: string, password: string, admin: boolean): Promise<Observable<any>> {
    let data = await this.http.post(url + 'users/' + companyID,
      {
        credentials: {
          password: password
        },
        profile: {
          firstName: firstName,
          lastName: lastName,
          email: email,
          phone: phone,
        },
        admin: admin
      }
    )
    return data;
  }

  // teammatesArray type needs to be a BasicUser Array
  async postTeam(companyID: number, teamName: string, teamDescription: string, teammatesArray: string[]): Promise<Observable<any>> {
    let data = await this.http.post(url + 'team/' + companyID,
      {
        name: teamName,
        description: teamDescription,
        teammates: teammatesArray
      })
    return data;
  }

  async postProject(teamID: number, projectName: string, projectDescription: string): Promise<Observable<any>> {
    let data = await this.http.post(url + 'projects/' + teamID,
      {
        name: projectName,
        description: projectDescription
      }
    )
    return data;
  }

  async editProjects(projectId: number, projectName: string, projectDescription: string, isActive: boolean): Promise<Observable<any>> {
    let data = await this.http.patch(url + 'projects/' + projectId,
      {
        name: projectName,
        description: projectDescription,
        active: isActive
      }
    )
    return data;
  }
}
