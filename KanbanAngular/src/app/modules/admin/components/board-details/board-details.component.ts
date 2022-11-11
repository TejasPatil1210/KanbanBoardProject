import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/services/api.service';
import { BackGroundImageService } from 'src/app/services/back-ground-image.service';

@Component({
  selector: 'app-board-details',
  templateUrl: './board-details.component.html',
  styleUrls: ['./board-details.component.css']
})
export class BoardDetailsComponent implements OnInit {
  @Input('app-board-details') color:any="";
  boardForm:FormGroup;
  // columnForm:FormGroup;
  checkBoardPgStat:boolean=true;
  checkColumnPgStat:boolean=false;
  checkBgPgStat:boolean=false;
  // values:any=['To do','In Progress','Done'];
  allImages:any=[]
  allFormValues:any=[]
  bName:any
  boardcolumns=new FormArray([new FormGroup({columnName:new FormControl('To do',Validators.required),taskList:new FormControl([])}),
  new FormGroup({columnName:new FormControl('In Progress',Validators.required),taskList:new FormControl([])}),
  new FormGroup({columnName:new FormControl('Done',Validators.required),taskList:new FormControl([])})])  
  // boardcolumns=new FormArray([new FormControl('To do',Validators.required),
  // new FormControl('In Progress',Validators.required),
  // new FormControl('Done',Validators.required)])
  // columnList:new FormArray([new FormGroup({ 
  //     columnName:new FormControl('',[Validators.required])})]),
  constructor(private bgImgService:BackGroundImageService,private apiServ:ApiService,private router:Router){ 
    this.boardForm=new FormGroup({
      projectName:new FormControl('',[Validators.required]),
      columnList:new FormArray([
        this.addFormGroup()
      ]),
      projectBgImage:new FormControl('',[Validators.required])
    })
  }
   
  onClickofBoardPageNext()
  {
    this.bName=this.boardForm.controls['projectName'].value
    // console.log(this.bName)
    console.log(this.boardForm.controls['projectName'].value)
    this.checkBoardPgStat=false;
    this.checkColumnPgStat=true;
    this.checkBgPgStat=false;
  }
  onClickofColumnPageNext()
  {
    // console.log(this.allFormValues)
    // //  this.values=this.boardForm.controls['boardcolumns'].value
    //  this.values.push(this.boardForm.controls['boardcolumns'].value)
    console.log(this.boardForm.controls['projectName'].value)
    console.log(this.boardcolumns.value)
    // console.log(this.formvalues)
    this.checkBoardPgStat=false;
    this.checkColumnPgStat=false;
    this.checkBgPgStat=true;
  }

  onClickofBgPageFinish()
  { 
    console.log(this.boardForm.controls['projectName'].value)
    console.log(this.boardcolumns.value)
  }
  onClickofColumnPageback()
  {
    this.checkBoardPgStat=true;
    this.checkColumnPgStat=false;
    this.checkBgPgStat=false;
  }
  onClickofBgPageBack()
  {
    this.checkBoardPgStat=false;
    this.checkColumnPgStat=true;
    this.checkBgPgStat=false;
  }

  onClickAdd()
  {
    this.boardcolumns.push(new FormGroup({columnName:new FormControl('',[Validators.required]),taskList:new FormControl([])})) ; 
    // this.allFormValues.push(this.boardForm.controls['boardcolumns'].value)
    //  this.values.push(this.boardForm.controls['boardcolumns'].value)
    // this.formvalues.push()
  }

  addGrpInArray():void{
    (<FormArray>this.boardForm.get('columnList')).push(this.addFormGroup());
  }
  addFormGroup():FormGroup
  {
    return new FormGroup({ 
           columnName:new FormControl('',[Validators.required]),
          taskList:new FormControl([],)})
  }

  // removFormgrp():void
  // {
  //   console.log(this.boardForm.value);
  //   const a=this.boardForm.controls['columnList'].value.length
  //   console.log(a);
  //   for(let i=0;i<a;i++)
  //   {
  //     (<FormArray>this.boardForm.get('columnList')).removeAt(i);
  //    }
    //  (<FormArray>this.boardForm.get('columnList')).push(this.addFormGroup());
  //   console.log(this.boardForm.value);
  // }
  onClickRemove(idx:number)
  {
    this.boardcolumns.removeAt(idx); 
  }

  // getFormGrp()
  // {
  //   return this.columnForm
  // }
  project!:any
  onClickFinish()
  {
    // this.removFormgrp()
   console.log(this.boardcolumns.value)
     for(let i=1;i<this.boardcolumns.length;i++)
     {
      this.addGrpInArray();
     }
     console.log(this.boardForm.value)
  //     console.log(this.boardcolumns.controls[i].value)  
  //     console.log(this.boardcolumns.controls[i].value)
      this.boardForm.controls['columnList'].setValue(this.boardcolumns.value)
    // this.boardForm.removeControl('columnList')
    // this.boardForm.addControl('columnList',this.boardcolumns.value)
  //   console.log(this.columnForm.value)
  //   this.createColumns(this.columnForm.value)
  //   }
    
  //   this.boardForm.controls['columnList']
   console.log(this.boardForm.value)
    this.boardForm.removeControl('columnName')
    this.apiServ.createProject(this.boardForm.value).subscribe(y=>{
      console.log(y);
      this.project=y
      console.log(this.project)
      const state={y}
      this.router.navigate(['/admin/home1/'],{ state })
    },
    (error)=>{
      console.log(error)
    })
    

  }

  
  
  // createColumns(colData:Column)
  // {
  //   const colList:Column[]=[]
  //   colList.push(colData);
  //   console.log(colData)
  //   const projectName:any=this.boardForm.controls['projectName'].value
    // this.boardForm.removeControl('projectName')
    // this.boardForm.removeControl('projectBgImage')
  //   console.log(colList.length)
  //   for(let i=0;i<colList.length;i++)
  //   {
  //     console.log(colList[i])
  //   this.apiServ.createColumn(colList[i],projectName).subscribe(x=>{
  //     console.log(x)
  //   },
  //   error=>{
  //       console.log(error)
  //   })
  // }
  // }

  onClickBasic()
  {
    //new FormArray([new FormGroup({columnName:new FormControl('To do',Validators.required)}),
  // new FormGroup({columnName:new FormControl('In Progress',Validators.required)}),
  // new FormGroup({columnName:new FormControl('Done',Validators.required)})])
     this.boardcolumns=new FormArray([new FormGroup({columnName:new FormControl('To do',Validators.required),taskList:new FormControl([])}),
     new FormGroup({columnName:new FormControl('In Progress',Validators.required),taskList:new FormControl([])}),
     new FormGroup({columnName:new FormControl('Done',Validators.required),taskList:new FormControl([])})])
  }

  onClickTimeDriven()
  {
    this.boardcolumns=new FormArray([new FormGroup({columnName:new FormControl('To do',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Scheduled - Soon',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Scheduled - Tomorrow',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Scheduled - Today',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('In progress',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Done',Validators.required),taskList:new FormControl([])})])
  }

  onClickEventDriven()
  {
    this.boardcolumns=new FormArray([new FormGroup({columnName:new FormControl('To do',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('To do - Waiting',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('To do - Ready',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('In progress',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Done',Validators.required),taskList:new FormControl([])})])
  }
  onClickTeamBasic()
  {
    this.boardcolumns=new FormArray([new FormGroup({columnName:new FormControl('To do',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('In progress - Waiting',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('In progress - Working',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Done',Validators.required),taskList:new FormControl([])})])
  }
  onClickProductDev()
  {
    this.boardcolumns=new FormArray([new FormGroup({columnName:new FormControl('Requirements gathering',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Requirements analysis',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('In development - Waiting',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('In development - Working on',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('In QA - Waiting',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('In QA - Working on',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('In verification - Waiting',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('In verification - Working',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Done',Validators.required),taskList:new FormControl([])})])
  }
  onClickSalesPipeline()
  {
    this.boardcolumns=new FormArray([new FormGroup({columnName:new FormControl('Leads',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('New opportunities',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Initial communication',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Solution development',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Proposal',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Solution evaluation',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Negotiation',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Closed - LOST',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Closed - WON',Validators.required),taskList:new FormControl([])})])
  }
  onClickOnlineMarketing()
  {
    this.boardcolumns=new FormArray([new FormGroup({columnName:new FormControl('Alerts',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('To do',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Scheduled - This week',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Scheduled - Today',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('In progress',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Monitoring',Validators.required),taskList:new FormControl([])}),
    new FormGroup({columnName:new FormControl('Done',Validators.required),taskList:new FormControl([])})])
  }
  getAllImg()
  {
    this.allImages=this.bgImgService.getAllImages();
  }
  
 // imgUrl:any
  onSelect(x:any)
  {
    console.log(x);
    
    this.boardForm.patchValue({
      projectBgImage:x 
      })
    // this.imgUrl=x;

    // console.log(this.imgUrl)
  }
  colName:any
  ngOnInit(): void {
    this.bName=this.boardForm.value
    // this.colName=this.boardcolumns.value.map(x=>x.columnName)
    console.log(this.boardcolumns.value)
   console.log(this.colName)
    console.log(this.bName)
    this.getAllImg()
  }

}
