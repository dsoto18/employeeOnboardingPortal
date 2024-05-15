# Full Stack Employee Onboarding Portal, with AngularJS frontend and Java Spring backend.

## To Run

Open the frontend folder in your IDE with the terminal open in the same directory, and use the following commands -
```bash
npm install
ng serve
```
Open the backend folder in another window and run the GroupFinalApplication.java file, project is setup with Oracle Open JDK version 17.
Project is supported by a PostgreSQL Database on localhost:5432 with the password 'bondstone'.
Then in your browser, navigate to the url - localhost:4200 
## Project Features Overview
### Login Page
Landing page after first navigating to the site, prompts for user credentials.
![alt text](https://github.com/dsoto18/employeeOnboardingPortal/blob/master/imgs/login.png?raw=true)
### Home Page
Home page for a non admin user after logging in. Page includes a list of announcements that continues down, only of announcements related to the user based on their projects and teams.
![alt text](https://github.com/dsoto18/employeeOnboardingPortal/blob/master/imgs/nonAdminView.png?raw=true)
### Teams Page
Teams page for a non admin user, where they can view teams they are apart of and other members of their teams.
![alt text](https://github.com/dsoto18/employeeOnboardingPortal/blob/master/imgs/nonAdminTeams.png?raw=true)
### Projects Page
Page for users to view projects based on their team, after clicking on a specific team from the Teams Page.
![alt text](https://github.com/dsoto18/employeeOnboardingPortal/blob/master/imgs/nonAdminProjects.png?raw=true)
### Admin Home Page
Landing page after a user with administrative privileges logs in. If an admin user logs in it will be noted on the top left of the page, also there will be two more options on the navigation bar: 'COMPANY' which allows the user to toggle between different companies if desired, and 'USERS' for the admin to view all users of the company.
![alt text](https://github.com/dsoto18/employeeOnboardingPortal/blob/master/imgs/adminHome.png?raw=true)
There is also now an option visible next to announcments allowing the admin to create announcements, which would bring up the following pop up and save a new announcment once completed.
![alt text](https://github.com/dsoto18/employeeOnboardingPortal/blob/master/imgs/adminAddAnnouncment.png?raw=true)
### Admin Users Page
Users page only visible to admins, which retrieves user information from the database and displays it in order for the admin to view.
![alt text](https://github.com/dsoto18/employeeOnboardingPortal/blob/master/imgs/adminUsers.png?raw=true)
### Admin Teams Page
Teams Page from the admin's view.
![alt text](https://github.com/dsoto18/employeeOnboardingPortal/blob/master/imgs/adminTeams.png?raw=true)
Clicking on the '+' brings the following pop up to create a new team, which is posted to the database after submmitted and then viewable from the teams page.
![alt text](https://github.com/dsoto18/employeeOnboardingPortal/blob/master/imgs/adminAddTeam.png?raw=true)
### Admin Projects
The Projects Page from the admin perspective looks very similar with the addition of the feature to create more projects, which looks like the following image.
![alt text](https://github.com/dsoto18/employeeOnboardingPortal/blob/master/imgs/adminAddProject.png?raw=true)
