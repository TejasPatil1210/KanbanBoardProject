import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { KanbanService } from 'src/app/services/kanban.service';
import { PasswordChkService } from 'src/app/services/password-chk.service';

@Component({
  selector: 'app-set-new-password',
  templateUrl: './set-new-password.component.html',
  styleUrls: ['./set-new-password.component.css']
})
export class SetNewPasswordComponent implements OnInit {

  setNewPasswordForm:FormGroup
  constructor(private router:Router,private passwdServ:PasswordChkService,private kanabanServ:KanbanService) {
    this.setNewPasswordForm=new FormGroup({
      newPassword:new FormControl('',[Validators.required,validatePass]),
      confPassword:new FormControl('',[Validators.required])
     })
   }

  ngOnInit(): void {
  }

  home(){
    this.router.navigateByUrl('home-page');
  }
  resetPassword()
  {
   if(this.setNewPasswordForm.value.newPassword === this.setNewPasswordForm.value.confPassword)
   {
    this.kanabanServ.getUser1(this.kanabanServ.getUserEmail()).subscribe(x=>{
      console.log(x);
      x.password=this.setNewPasswordForm.value.newPassword;
      console.log(x);
      this.kanabanServ.updateUser1(x).subscribe(y=>{
        console.log(y); 
        this.router.navigate(['../login'])
      },error=>{
        console.log(error);
      })
    },error=>{
      console.log(error);
    })
   }
   else{
    console.log('Hello');
    alert('Passwords are not matching')
    this.setNewPasswordForm.controls['confPassword'].invalid
   } 
  }



}

export function validatePass(control:AbstractControl){
  let pass:string=control.value  
  PasswordChkService.prototype.password=pass;
  if(pass.length >=6)
  {
    return null;
  }
  else
  {
    return {myError:false}
  }
}

export function validateconfPass(control:AbstractControl){
  const confpass=control.value
  console.log(confpass);
  const pass= PasswordChkService.prototype.getPassword()
  console.log(pass);
  
  if(confpass === pass)
  {
  return null
  }
  else{
    return {mayErr1:false}
  }
}
