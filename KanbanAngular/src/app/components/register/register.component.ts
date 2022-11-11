import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { map, Observable, shareReplay } from 'rxjs';
import { User } from 'src/app/class/user';
import { KanbanService } from 'src/app/services/kanban.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm=new FormGroup({
    userEmail:new FormControl("",[Validators.required,Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
    
    userName:new FormControl("",[Validators.required,Validators.pattern("(^[a-zA-Z][a-zA-Z\s]{0,20}[a-zA-Z]$)")]),
    password:new FormControl("",[Validators.required]),
    confirm_password:new FormControl("",[Validators.required])
  })
  constructor(private router: Router, private breakpointObserver: BreakpointObserver,private service:KanbanService) { }
  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
   .pipe(
     map(result => result.matches),
     shareReplay()
   );
   
  ngOnInit(): void {
  }
  user:User={} as User;
  flag=false;
  
  submit()
  {
    this.user.userEmail=<string>this.registerForm.value.userEmail;
    this.user.userName=<string>this.registerForm.value.userName;
    this.user.password=<string>this.registerForm.value.password;
    this.service.saveuser(this.user).subscribe(s=>{
      console.log("user saved\n"+s.userName);
     if ((this.registerForm.value.userName != '' && this.registerForm.value.userEmail != '' && this.registerForm.value.password != '') && 
    (this.registerForm.value.userName != null && this.registerForm.value.userEmail != null && this.registerForm.value.password != null))
    {
        this.flag=true;
        this.router.navigate(["login"])
      }
    }) 
    if(this.flag==true)
    {
      this.router.navigate(["login"])
      this.flag=false;
    }
  
  
  }
 

  login()
  {
    console.log("log");
    this.router.navigateByUrl('/login');
  }
  
  home(){
    this.router.navigateByUrl('home-page');
  }
 
}
