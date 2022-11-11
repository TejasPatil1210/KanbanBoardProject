import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ProjectMember } from 'src/app/class/project-member';
import { ApiService } from 'src/app/services/api.service';
import { GetProjectDetailsService } from 'src/app/services/get-project-details.service';

@Component({
  selector: 'app-edit-dialog-box',
  templateUrl: './edit-dialog-box.component.html',
  styleUrls: ['./edit-dialog-box.component.css']
})
export class EditDialogBoxComponent implements OnInit {
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
      console.log('s:'+ s);
      
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
    this.getTask()
  }
  myStyles:any
  // backColor()
  // {
  //   console.log("Hiiii "+this.NewTask);
    
  // if(this.NewTask.controls['cardType'].value == 'Default')
  // {
  // this.myStyles = {
  //   backgroundColor: 'rgba(47, 193, 246, 0.997)',
  //   color:'black'    
  // }
  // if(this.NewTask.controls['cardType'].value == 'Urgent Work')
  // {
  // this.myStyles = {
  //   backgroundColor: 'rgb(246, 63, 158)',
  //   color:'whitesmoke'    
  // }
  // }
  // }
  // }
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
  taskId:any
  update()
  {
    this.projName=this.getProjDetServ.getProjName();
    this.colName=this.getProjDetServ.getColName();
    this.apiService.updateTask(this.projName,this.colName,this.NewTask.value).subscribe(x=>{
      console.log(x);
    },error=>{
      console.log(error);
      
    })   
  }

  getTask()
  {
    this.projName=this.getProjDetServ.getProjName();
    this.colName=this.getProjDetServ.getColName();
    this.taskId=this.getProjDetServ.getTaskId();
    this.apiService.getTask(this.projName,this.colName,this.taskId).subscribe(data=>{
      console.log(data);
      this.NewTask.setValue(data);
      console.log(this.NewTask);
      
      if(this.NewTask.controls['cardType'].value === 'Default')
      {
        console.log(this.NewTask.controls['cardType'].value);
        this.myStyles = {
        backgroundColor: 'rgba(47, 193, 246, 0.997)',
        color:'black'    
      }
    }
      console.log(this.NewTask.controls['cardType'].value);
      if(this.NewTask.controls['cardType'].value === "Urgent Work")
      {
        console.log(this.NewTask.controls['cardType'].value);
        
        this.myStyles = {
        backgroundColor: 'rgb(246, 63, 158)',
        color:'whitesmoke'    
      }
    }
  }
    ,error=>{
      console.log(error);
      
    })

  }

  assignee(name:String)
  {
    console.log(this.NewTask.value.assignee);
    
     this.NewTask.value.memberName=name
  }
}
