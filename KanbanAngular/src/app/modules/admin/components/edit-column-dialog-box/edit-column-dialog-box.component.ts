import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ApiService } from 'src/app/services/api.service';
import { GetProjectDetailsService } from 'src/app/services/get-project-details.service';

@Component({
  selector: 'app-edit-column-dialog-box',
  templateUrl: './edit-column-dialog-box.component.html',
  styleUrls: ['./edit-column-dialog-box.component.css']
})
export class EditColumnDialogBoxComponent implements OnInit {
  columnForm!:FormGroup
  constructor(private getProjDetails:GetProjectDetailsService,private apiserv:ApiService) {this.columnForm=new FormGroup({
    columnName:new FormControl('',[Validators.required]),
  })}
  
  projName:any
  colName:any
  colData:any
  updateColumn()
  {
    this.projName=this.getProjDetails.getProjName();
    this.colData=this.getProjDetails.getEditColData()
    this.colData.columnName=this.columnForm.controls['columnName'].value
    console.log(this.colData);
    this.apiserv.updateColumn(this.colData,this.projName).subscribe(x=>{
      console.log(x);
    },error=>{
      console.log(error);    
    })
  }
  

  ngOnInit(): void {
    this.getColumn()
  }
  getColumn()
  {
     this.colName=this.getProjDetails.getEditColName();
     this.projName=this.getProjDetails.getProjName();
    //  this.colName=this.getProjDetServ.getColName();
    // this.taskId=this.getProjDetServ.getTaskId();
    this.apiserv.getColumn(this.projName,this.colName).subscribe(data=>{
      console.log(data);
      this.getProjDetails.editColData=data;
      this.columnForm.controls['columnName'].setValue(data.columnName)
    },error=>{
      console.log(error);
      alert('Column Not Found')      
    })

  }

}
