import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, Subject, tap } from 'rxjs';
import { Project } from '../class/project';
import { ProjectMember } from '../class/project-member';
import { User } from '../class/user';
import { User1 } from '../class/User1';

@Injectable({
  providedIn: 'root'
})
export class KanbanService {
  
  currentuser:User={}as User
  constructor(private router: Router,private http: HttpClient) { }
  // setToken(token: string): void {
  //   localStorage.setItem('token', token);
  // }

  // getToken(): string | null {
  //   return localStorage.getItem('token');
  // }
  private refresh=new Subject<void>();   //Subject constructor creates a new Observable where we can emit values from
  get refreshNeeded(){
      return this.refresh;
  }
  isLoggedIn() {
    let token=localStorage.getItem("token");
    if(token==undefined || token==='' || token==null){
      return false;
    }else{
      return true;
    }
   
  }
  
  otp:any


  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['login']);
    localStorage.removeItem('userEmail');
    localStorage.removeItem('localDate');
    localStorage.removeItem('localTime');
  }
  addMember(email:any,data:ProjectMember){

    return this.http.post<ProjectMember>('http://localhost:9000/api/v2/kanban-service/add-project-member/'+email,data)
    .pipe(
      tap(() => {             //tap operation will let us change something in the service and
        this.refresh.next();   // we can call the next method triggers the next subject
      })
    );
  }
  
  getMembers(email:any){
    return this.http.get<ProjectMember[]>('http://localhost:9000/api/v2/kanban-service/project-members/'+email);

  }

 
  

  
  
 // getuserUrl="http://localhost:9000/api/v2/kanban-service/getuser/"
 // currentuser:User={} as User;

  saveuserUrl="http://localhost:9000/api/v2/kanban-service/user";
  saveuser(user:User)
  {
    alert("Registration Successful")
    console.log("Fetching");
    
    return this.http.post<User>(this.saveuserUrl,user);
    
  }

  authenticateUrl="http://localhost:9000/api/v1/login"
  authserver(user:User)
  {
    console.log("Authenticate");
    return this.http.post(this.authenticateUrl,user)
  }
  
  loginUser(token:any, userEmail: any){
   var cDate = new Date();
   var date = cDate.getDate()+ ":" + (cDate.getMonth()+1) + ":" + cDate.getFullYear();
   var time = cDate.getHours()+ ":" +
   cDate.getMinutes() + ":" +
   cDate.getSeconds();
  
    localStorage.setItem("token",token);
    localStorage.setItem("userEmail", userEmail);
    
    localStorage.setItem("loginTime",time);
    localStorage.setItem("loginDate",date);
    return true;
  }
 
  // users = 'http://localhost:9000/api/v1/users';
  // getAllUsers(){
  //   return this.http.get(this.users);
  // }

  getuserUrl= 'http://localhost:9000/api/v2/kanban-service/find-user/';
  getUser(email:any){
    return this.http.get<User>(this.getuserUrl+email);
  }
  getPassword(email:any){
    
    return this.http.get('http://localhost:9000/api/v1/forgot-password/'+email,{responseType:"text"});
  }
  
  updateUserUrl= 'http://localhost:9000/api/v2/kanban-service/update-user';
  update(data:any){
   return this.http.put<User>(this.updateUserUrl,data);
  }

  // getuserUrl="http://localhost:9000/api/v2/kanban-service/getuser/"

  // getuser(email:string)
  // {
  //   let url=this.getuserUrl+email
  //   return this.http.get<User>(url);
  // }

  
  getProjects(email:any){
    return this.http.get<Project[]>('http://localhost:9000/api/v2/kanban-service/projects/'+email);

  }
  
  getUser1(userEmail:any)
  {
    return this.http.get<User1>('http://localhost:9000/api/v1/get-user/'+userEmail)
  }

  updateUser1(userData:User1)
  {
    return this.http.put<User1>('http://localhost:9000/api/v1/update-user',userData)
  }
  checkOtp()
  {
    return this.otp;
  }

  userEmail:any;
  getUserEmail()
  {
    return this.userEmail;
  }
}
