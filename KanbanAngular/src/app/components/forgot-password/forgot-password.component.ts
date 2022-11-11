import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { User } from 'src/app/class/user';
import { KanbanService } from 'src/app/services/kanban.service';


@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  data?:boolean;

   passwordResetForm=new FormGroup({
    userEmail:new FormControl('',[Validators.required,Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")])
   })
  constructor(private service:KanbanService,private dialog:MatDialog,private router:Router) { }
  user:User=new User();
  userEmail1:any=window.localStorage.getItem('userEmail');
  ngOnInit(): void {
  }
  login()
  {
    console.log("log");
    this.router.navigateByUrl('/login');
  }
  
  home(){
    this.router.navigateByUrl('home-page');
  }
  resetPassword(){
  this.service.getPassword(this.passwordResetForm.value.userEmail).subscribe((x)=>{
    console.log(x);
    this.service.otp=x;
    console.log(this.passwordResetForm.value.userEmail);
    this.service.userEmail=this.passwordResetForm.value.userEmail;
     alert("OTP is sent to your registered email")
      console.log("OTP is sent to your registered email");
      this.router.navigate(['../check-otp'])

  //  if(this.user.userEmail=x){
  //     this.data = true;
  //         }
    // else{
     
    //   this.data = false;
    //   console.log('------');
      
    //   alert("email is not registered")
    // }
   },
   
   (err) => {
    // console.log(err)
    // this.data = false;
    alert("Invalid User Email")
 }
   
  )}
}

