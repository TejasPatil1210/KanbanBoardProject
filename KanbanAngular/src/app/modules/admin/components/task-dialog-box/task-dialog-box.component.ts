import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ProjectMember } from 'src/app/class/project-member';
import { ApiService } from 'src/app/services/api.service';
import { GetProjectDetailsService } from 'src/app/services/get-project-details.service';

@Component({
  selector: 'app-task-dialog-box',
  templateUrl: './task-dialog-box.component.html',
  styleUrls: ['./task-dialog-box.component.css']
})
export class TaskDialogBoxComponent implements OnInit {
  currentDate:any=new Date();
  
  selectedMatDate!: Date;
  ordinaryDateSelected !: Date;
  panelOpenState = false;
  projectmember:ProjectMember[]=[];
  projMemberList:ProjectMember[]=[];
  NewTask!:FormGroup
  isEditEnabled:boolean=this.getProjDetServ.getEditEnabled()
  constructor(private apiService:ApiService,private getProjDetServ:GetProjectDetailsService) {
    // this.apiService.currentuser.userEmail="check"
    // this.apiService.currentuser.userName="check"
    // this.apiService.currentuser.password="check"
    apiService.getprojectmember().subscribe(s=>{
      this.projectmember=<ProjectMember[]>s
      
      console.log(this.projectmember.length);
      for(let i=0;i<this.projectmember.length;i++)
      {
        if(this.projectmember[i].noOfTask < 3)
        {
          this.projMemberList.push(this.projectmember[i]);
        }
      }
      console.log(this.projMemberList);
     
    },
    err=>{})
    this.NewTask=new FormGroup({
      taskId:new FormControl("",),
      taskName:new FormControl("",[Validators.required]),
      taskDescription:new FormControl(""),
      priority:new FormControl("",[Validators.required]),
      cardType:new FormControl("",[Validators.required]),
      dueDate:new FormControl(""),
      memberName:new FormControl("",[Validators.required]),
    },
   );
   }

   
  ngOnInit(): void {
  }

  myStyles = {
    backgroundColor: 'rgba(47, 193, 246, 0.997)',
    color:'black'
  }
  colorred()
  {
    console.log("change color");
    this.myStyles.backgroundColor='rgb(246, 63, 158)' 
    this.myStyles.color='whitesmoke'
    console.log(this.myStyles.backgroundColor);
    
  }
  coloryellow()
  {
    this.myStyles.backgroundColor='rgba(47, 193, 246, 0.997)'
    this.myStyles.color='black'
    console.log(this.myStyles.backgroundColor);
  }
  projName:any
  colName:any
  create()
  {
    this.projName=this.getProjDetServ.getProjName();
    this.colName=this.getProjDetServ.getColName();
    this.apiService.createTask(this.projName,this.colName,this.NewTask.value).subscribe(x=>{
      console.log(x);
    },error=>{
      console.log(error);
      
    })   
  }

  assignee(name:String)
  {
     this.NewTask.value.assignee=name
  }
}



