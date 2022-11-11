import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ApiService } from 'src/app/services/api.service';
import { GetProjectDetailsService } from 'src/app/services/get-project-details.service';

@Component({
  selector: 'app-column-dialog-box',
  templateUrl: './column-dialog-box.component.html',
  styleUrls: ['./column-dialog-box.component.css']
})
export class ColumnDialogBoxComponent implements OnInit {

  columnForm!:FormGroup
  // private String columnId;
  // private String columnName;
  // private List<Task> taskList;
  constructor(private getProjDetails:GetProjectDetailsService,private apiserv:ApiService) {this.columnForm=new FormGroup({
    columnName:new FormControl('',[Validators.required]),
    taskList:new FormControl([])
  })}
  
  projName:any
  createColumn()
  {
    this.projName=this.getProjDetails.getProjName();
    this.apiserv.createColumn(this.columnForm.value,this.projName).subscribe(x=>{
      console.log(x);
    },error=>{
      console.log(error);    
    })
  }

  ngOnInit(): void {
  }
}
