import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { map, Observable, shareReplay } from 'rxjs';
import { KanbanService } from 'src/app/services/kanban.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isplaylistpresent=false
  constructor(private router:Router,private breakpointObserver: BreakpointObserver, private service:KanbanService) {
    // this.initials=<string>this.service.currentuser.userName.substring(0,1).toUpperCase();
    // this.name=<string>this.service.currentuser.userName.toUpperCase();
    // console.log(service.currentuser.userName);
    // if(service.currentuser.projectLists!=null)
    // {
    //   this.isplaylistpresent=true;
    // }
    
   }
     
name:string=localStorage.getItem('userEmail')!;
a:string=this.name.slice(0,-10);
initials:string=this.name.substring(0,1);
  ngOnInit(): void {
    // this.initials=this.service.currentuser.userName.substring(0)
   // console.log(this.initials);
    console.log(this.name);
    
  }
  
  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
  .pipe(
    map(result => result.matches),
    shareReplay()
  );
  myprofile()
  {
    
      console.log("profile");
      this.router.navigateByUrl('/admin/my-profile')
   
   

  }
  signout()
  {
    console.log("logged out");
    this.service.logout();
    this.router.navigateByUrl('login' )
  }


  login()
{
  console.log("log");
  this.router.navigateByUrl('login');
}

}
