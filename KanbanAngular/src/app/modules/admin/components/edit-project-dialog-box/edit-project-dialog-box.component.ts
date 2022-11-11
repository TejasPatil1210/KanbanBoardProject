import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ApiService } from 'src/app/services/api.service';
import { GetProjectDetailsService } from 'src/app/services/get-project-details.service';

@Component({
  selector: 'app-edit-project-dialog-box',
  templateUrl: './edit-project-dialog-box.component.html',
  styleUrls: ['./edit-project-dialog-box.component.css']
})
export class EditProjectDialogBoxComponent implements OnInit {

  projectForm!:FormGroup
  constructor(private getProjDetails:GetProjectDetailsService,private apiserv:ApiService) {
    this.projectForm=new FormGroup({
    projectName:new FormControl('',[Validators.required]),
  })}
  
  projName:any
  projData:any
  updateProject()
  {
    //this.projName=this.getProjDetails.getProjName();
    this.projData=this.getProjDetails.getEditProjData();
   console.log(this.projData.projectName);
   
    this.projData.projectName=this.projectForm.controls['projectName'].value
    console.log(this.projData);
    
    this.apiserv.updateProject(this.projData).subscribe(x=>{
      console.log(x);
    },error=>{
      console.log(error);    
    })
  }
  

  ngOnInit(): void {
    this.getProject()
  }

  getProject()
  {
     this.projName=this.getProjDetails.getEditProjName();
    //  this.colName=this.getProjDetServ.getColName();
    // this.taskId=this.getProjDetServ.getTaskId();
    this.apiserv.getProject(this.projName).subscribe(data=>{
      console.log(data);
      this.getProjDetails.editProjData=data;
      this.projectForm.controls['projectName'].setValue(data.projectName)
    },error=>{
      console.log(error);
      alert('Project Not Found')
    })

  }

}
