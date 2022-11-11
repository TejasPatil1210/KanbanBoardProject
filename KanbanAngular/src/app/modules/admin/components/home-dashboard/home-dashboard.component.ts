import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/class/user';
import { KanbanService } from 'src/app/services/kanban.service';

@Component({
  selector: 'app-home-dashboard',
  templateUrl: './home-dashboard.component.html',
  styleUrls: ['./home-dashboard.component.css']
})
export class HomeDashboardComponent implements OnInit {
  isplaylistpresent=false
  constructor(private service:KanbanService,private router:Router) { 
    // if(service.currentuser.projectLists!=null)
    // {
    //   this.isplaylistpresent=true;
    // }
  }
  name:string=""
initials:string="";
  ngOnInit(): void {
    // this.initials=this.service.currentuser.userName.substring(0)
    console.log(this.initials);
    
  }

 
  myprofile()
  {
    console.log("profile");
    

  }
  signout()
  {
    console.log("register");
   // this.service.currentuser={}as User
    this.router.navigateByUrl("/register" )
  }


  login()
{
  console.log("log");
  this.router.navigateByUrl('/login');
}
CreateProject()
{
  this.router.navigateByUrl('/admin/home');
    console.log("Create project");
    
}

}

 

