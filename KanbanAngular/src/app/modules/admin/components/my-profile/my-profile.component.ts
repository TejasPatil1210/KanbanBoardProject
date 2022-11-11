import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { User } from 'src/app/class/user';
import { KanbanService } from 'src/app/services/kanban.service';
import { ChangePasswordComponent } from '../change-password/change-password.component';
import { EditProfileComponent } from '../edit-profile/edit-profile.component';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {
  public user:User=new User;
  //userName:any=window.localStorage.getItem('userName');
  userEmail:any=window.localStorage.getItem('userEmail');
  time:any=window.localStorage.getItem('loginTime');
  date:any=window.localStorage.getItem('loginDate');
  constructor(private service:KanbanService,private router:Router,private dialog:MatDialog) { }
  userName:string="";

  ngOnInit(): void {
    this.getUser();
   // this.service.getUser(localStorage.getItem())
  }
  getUser(){
    this.service.getUser(localStorage.getItem('userEmail')).subscribe(
      (response)=>{
        this.user=response;
        
        console.log(this.user);
        
      },
      err=>{
        alert("Error while fetching data!!")
        console.log(err.message);
        
      }
    )
    }
    editProfile() {
      //console.log(this.user);
      this.service.userEmail=localStorage.getItem('userEmail');
      this.dialog.open(ChangePasswordComponent,{
        data:this.user

      })
     // this.router.navigateByUrl('/admin/edit-profile');
    }
  
}

