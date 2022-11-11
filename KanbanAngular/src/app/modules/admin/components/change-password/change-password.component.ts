import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { User } from 'src/app/class/user';
import { KanbanService } from 'src/app/services/kanban.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  data1?:boolean;
  user:User=new User();
 // oldPassword=window.localStorage.getItem('password')
  resetForm=new FormGroup({
    oldPassword:new FormControl('',[Validators.required]),
    resetPassword:new FormControl('',[Validators.required]),
    confirmPassword:new FormControl('',[Validators.required])
   })
  constructor(public dailog: MatDialogRef<ChangePasswordComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,private service:KanbanService,private router:Router) { }

  ngOnInit(): void {
  }
  userEmail:any
  userdata:any
  submit(){
    // console.log(this.data.password);
    console.log(this.service.getUserEmail());
    this.userEmail=this.service.getUserEmail();
    this.service.getUser1(this.userEmail).subscribe(x=>{
      this.userdata=x;
      console.log(this.userdata);
      console.log(this.userdata.password);
    if(this.userdata.password === this.resetForm.value.oldPassword){
    this.userdata.password=this.resetForm.value.resetPassword;
    console.log(this.userdata);
    
    this.service.updateUser1(this.userdata).subscribe(x=>{
      this.data1 = true;
      console.log(x);
      alert('password has reset successfully')
      this.dailog.close();
      this.router.navigateByUrl('login')
    })
  }
  else{
    alert("Old password is incorrect")
  }     
    },
    error=>{
      console.log(error);
      
    })

    
}
}
