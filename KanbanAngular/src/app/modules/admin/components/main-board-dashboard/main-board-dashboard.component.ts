import { CdkDrag, CdkDragDrop, CdkDragEnter, CdkDragMove, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Column } from 'src/app/class/column';
import { Project } from 'src/app/class/project';
import { ApiService } from 'src/app/services/api.service';
import { BoardNavigationService } from 'src/app/services/board-navigation.service';
import { GetProjectDetailsService } from 'src/app/services/get-project-details.service';
import { ColumnDialogBoxComponent } from '../column-dialog-box/column-dialog-box.component';
import { EditColumnDialogBoxComponent } from '../edit-column-dialog-box/edit-column-dialog-box.component';
import { EditDialogBoxComponent } from '../edit-dialog-box/edit-dialog-box.component';
import { TaskDialogBoxComponent } from '../task-dialog-box/task-dialog-box.component';

@Component({
  selector: 'app-main-board-dashboard',
  templateUrl: './main-board-dashboard.component.html',
  styleUrls: ['./main-board-dashboard.component.css']
})
export class MainBoardDashboardComponent implements OnInit {

  @Input()
  projName!:string
  todoForm!:FormGroup;
  todo:any[]=[];
  inprogress:any[]=[]
  done:any[]=[]
  updateId!:any;
  allColumns:any
  allProjects:any
  allTasks:any
  isEditEnabled:boolean=false;
  constructor(private breakpointObserver: BreakpointObserver,private apiserv:ApiService,private boardNavServ:BoardNavigationService,
    private activatedRoute: ActivatedRoute,private router:Router,public dialog: MatDialog,private getProjDet:GetProjectDetailsService ) {
      this.activatedRoute.params.subscribe(data=>{
        this.projName=data['projectName'];
        console.log(this.projName);
        this.getColumns(this.projName)
      })
    }
  ngOnInit(): void {
    // this.todoForm=new FormGroup({
    //   item:new FormControl('',Validators.required)
    // })
    //  this.getColumns(this.projName)
      console.log("hiiiii");
      //
  }

  openDialog(x:any) {
    console.log("column Name"+x);
    this.getProjDet.columnName=x
    const dialogRef = this.dialog.open(TaskDialogBoxComponent)
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
      this.getColumns(this.projName)
    });
  }

  openDialog1(x:any,taskId:any) {
    console.log("column Name"+x);
    console.log(taskId)
    this.getProjDet.taskId=taskId;
    this.getProjDet.columnName=x
    const dialogRef = this.dialog.open(EditDialogBoxComponent)
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
      this.getColumns(this.projName)
    });
  }

  addTask()
  {
    this.todo.push({
      taskDescription:this.todoForm.value.item,
      done:false
    })
    this.todoForm.reset();
  }

  updateTask()
  {
    this.todo[this.updateId].taskDescription=this.todoForm.value.item;
    this.todo[this.updateId].done=false;
    this.todoForm.reset();
    this.updateId=undefined;
    this.isEditEnabled=false;
    
  }
  pName:any
  deleteTask(colName:any,taskId:any){
    // this.todo.splice(i,1);
    // this.getColumns(this.projName)
        this.pName=this.getProjDet.getProjName();
        this.apiserv.deleteTaskFromAnyCol(this.pName,colName,taskId).subscribe(x=>{
          console.log(x);
          this.getColumns(this.pName)
        },error=>{
          console.log(error);
          
        })    

  }
  

  noReturnPredicate(event: CdkDrag<string>){
    console.log("Hello Hiiii");
    console.log(event.dropContainer.id);
    
    if(event.dropContainer.id === 'Done'){ return false;}
   return true;
  }


  colLen:any
  getColumns(projName:any)
  {
    console.log(projName);
    
    // console.log(this.boardNavServ.getprojName().projectName.value)
    this.apiserv.getAllCols(projName).subscribe(x=>{
      console.log(x);
      this.allColumns=x;
      this.ngOnInit()
      // this.colLen=this.allColumns.length
      // console.log(this.allColumns.length)
      // this.ngOnInit()
    },
    error=>{
          console.log(error);
    })
   
  }
  // getArray(){
  //   let a; 
  //   for(let i=0;i<5;i++)
  //   {}
  // }


  onEdit(item:any,i:number){
    this.todoForm.controls['item'].setValue(item.taskDescription);
    this.updateId=i;
    this.isEditEnabled=true;
  }
  dropItem(event: CdkDragDrop<any[]>) {
    // if(event.previousContainer.id === 'Done' )
    // {
    //   return
    // }
    if (event.previousContainer === event.container) {
        console.log("inside if")
        console.log(event.previousContainer.id)
        console.log(event.previousContainer.data)
        moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);

        
        this.apiserv.getColumn(this.projName,event.previousContainer.id).subscribe(x=>{
          console.log(x);
          x.taskList=event.previousContainer.data;
          console.log(x);
          this.updateColumn(x,this.projName)
        },error=>{
          console.log(error);
        })
    } else {
      console.log("inside else")
      console.log(event)
      console.log("previous id"+event.previousContainer.id)
      let a=event.currentIndex
      console.log(a)      
                 
      
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex,
      );

      // console.log(event.container.data[a])
      console.log(event);
      
      this.getColname(event.previousContainer.id,event.container.id,event.container.data[a].taskId,event.container.data[a])
      // this.getColname() 
    }
  }

  getConnectedList(): any[] {
    return this.allColumns.map((x: { columnName: any; }) => `${x.columnName}`);
  }

  dropGroup(event: CdkDragDrop<any[]>) {
    if (event.previousContainer === event.container) {
        console.log("inside if")
        console.log(event)
        console.log(event.previousContainer.data)
        console.log(event.container.data)
        moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
      }
    //  else {
    //   console.log("inside else")
    //   console.log(event)
    //   console.log("prvious id"+event.previousContainer.id)
    //   let a=event.currentIndex
    //   console.log(a)      
                 
      
    //   transferArrayItem(
    //     event.previousContainer.data,
    //     event.container.data,
    //     event.previousIndex,
    //     event.currentIndex,
    //   );

      // console.log(event.container.data[a])
      console.log(event);
     
      
      this.apiserv.getProject(this.projName).subscribe(x=>{
        console.log(x);
        x.columnList=event.container.data;
        console.log(x);
        this.updateProject(x,this.projName)
      },error=>{
        console.log(error);
        
      })
      // this.getColname(event.previousContainer.id,event.container.id,event.container.data[a].taskId,event.container.data[a])
      // this.getColname() 
    //  }
  }

  updateProject(projData:Project,projectName:any)
  {
    this.apiserv.updateProject(projData).subscribe(data=>{
      console.log(data);
      this.getColumns(projectName)
    },error=>{
      console.log(error);
    })
  }

  updateColumn(colData:Column,projectName:any)
  {
    this.apiserv.updateColumn(colData,projectName).subscribe(data=>{
      console.log(data);
      this.getColumns(projectName)
    },error=>{
      console.log(error);
    })
  }
  taskData!:Task;
  addColName!:string;
  colLength!:number
  getColname(previousContid:string,currentContid:string,taskId:string,data:Task)
  {
    console.log("pid"+previousContid);
    console.log("cid"+currentContid);
    
    console.log(data)
    console.log(previousContid)
    console.log(currentContid);
    
    console.log(this.allColumns[previousContid.substring(previousContid.length-1)])
    console.log(this.allColumns[currentContid.substring(currentContid.length-1)])
      this.taskData=data;
     this.addColName=currentContid;
    //  this.addTaskintoCurCol(this.allColumns[currentContid.substring(currentContid.length-1)].columnName,data)
     this.deleteTaskfromPrevCol(previousContid,taskId)
  }

  deleteTaskfromPrevCol(colName:string,taskId:string)
  { 
    // console.log(colName)
    // console.log(data)
    this.apiserv.deleteTask(this.projName,colName,taskId).subscribe(x=>{
      console.log(x)
      console.log("task successfully deleted")
      this.addTaskintoCurCol(this.addColName,this.taskData)
    },error=>{
      console.log(error)
    })
  }

  addTaskintoCurCol(colName:string,data:Task)
  {
    this.apiserv.createTask(this.projName,colName,data).subscribe(x=>{
      console.log(x)
      console.log("task successfully created")
      // let url="home/showboard/:"+this.projName;
      // this.reloadComponent(true,url) 
    },error=>{
      console.log(error)
    })
  }

  OpenColumnDialog()
  {
    const dialogRef = this.dialog.open(ColumnDialogBoxComponent)
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
      this.getColumns(this.projName)
    });
    this.getColumns(this.getProjDet.getProjName())
  }

  dragEntered(event: CdkDragEnter<any>) {
    const drag = event.item;
    const dropList = event.container;
    const dragIndex = drag.data;
    const dropIndex = dropList.data;
    console.log(event);
    
     this.dragDropInfo = { dragIndex, dropIndex };
    console.log('dragEntered', { dragIndex, dropIndex });

    const phContainer = dropList.element.nativeElement;
    const phElement = phContainer.querySelector('.cdk-drag-placeholder');
    console.log(phElement);
    if (phElement) {
      phContainer.removeChild(phElement);
      phContainer.parentElement?.insertBefore(phElement, phContainer);

      moveItemInArray(this.allColumns, dragIndex, dropIndex);
      console.log(this.allColumns);
    }

  }
  @ViewChild('dropListContainer') dropListContainer?: ElementRef;
  dropListReceiverElement?: HTMLElement;
  dragDropInfo?: {
    dragIndex: number;
    dropIndex: number;
  };

  dragMoved(event: CdkDragMove<number>) {
    if (!this.dropListContainer || !this.dragDropInfo) return;

    const placeholderElement =
      this.dropListContainer.nativeElement.querySelector(
        '.cdk-drag-placeholder'
      );

    const receiverElement =
      this.dragDropInfo.dragIndex > this.dragDropInfo.dropIndex
        ? placeholderElement?.nextElementSibling
        : placeholderElement?.previousElementSibling;

    if (!receiverElement) {
      return;
    }

    receiverElement.style.display = 'none';
    this.dropListReceiverElement = receiverElement;
  }

  dragDropped(event: CdkDragDrop<number>) {
    if (!this.dropListReceiverElement) {
      return;
    }

    this.dropListReceiverElement.style.removeProperty('display');
    this.dropListReceiverElement = undefined;
    this.dragDropInfo = undefined;
    this.apiserv.getProject(this.projName).subscribe(x=>{
      console.log(x);
      x.columnList=this.allColumns;
      console.log(x);
      this.updateProject(x,this.projName)
    },error=>{
      console.log(error);
    })
  }





  dragEntered1(event: CdkDragEnter<any>) {
    const drag = event.item;
    const dropList = event.container;
    const dragIndex = drag.data;
    const dropIndex = dropList.data;
    console.log(event);
    
     this.dragDropInfo1 = { dragIndex, dropIndex };
    console.log('dragEntered1', { dragIndex, dropIndex });

    const phContainer = dropList.element.nativeElement;
    const phElement = phContainer.querySelector('.cdk-drag-placeholder');
    console.log(phElement);
    
    if (phElement) {
      phContainer.removeChild(phElement);
      phContainer.parentElement?.insertBefore(phElement, phContainer);

      moveItemInArray(this.allColumns, dragIndex, dropIndex);
      console.log(this.allColumns.controls);
    }

  }
  @ViewChild('dropListContainer1') dropListContainer1?: ElementRef;
  dropListReceiverElement1?: HTMLElement;
  dragDropInfo1?: {
    dragIndex: number;
    dropIndex: number;
  };

  dragMoved1(event: CdkDragMove<number>) {
    if (!this.dropListContainer1 || !this.dragDropInfo1) return;

    const placeholderElement =
      this.dropListContainer1.nativeElement.querySelector(
        '.cdk-drag-placeholder'
      );

    const receiverElement =
      this.dragDropInfo1.dragIndex > this.dragDropInfo1.dropIndex
        ? placeholderElement?.nextElementSibling
        : placeholderElement?.previousElementSibling;

    if (!receiverElement) {
      return;
    }

    receiverElement.style.display = 'none';
    this.dropListReceiverElement = receiverElement;
  }

  dragDropped1(event: CdkDragDrop<number>) {
    if (!this.dropListReceiverElement) {
      return;
    }

    this.dropListReceiverElement.style.removeProperty('display');
    this.dropListReceiverElement = undefined;
    this.dragDropInfo1 = undefined;
    this.apiserv.getProject(this.projName).subscribe(x=>{
      console.log(x);
      x.columnList=this.allColumns;
      console.log(x);
      this.updateProject(x,this.projName)
    },error=>{
      console.log(error);
    })
  }

  // @ViewChild(CdkDropList) dropList?: CdkDropList;
  // @Input() container: Task | undefined;

  // allowDropPredicate = (drag: CdkDrag, drop: CdkDropList) => {
  //   return this.isDropAllowed(drag, drop);
  // };

  // ngAfterViewInit(): void {
  //   if (this.dropList) {
  //     this.dragDropService.register(this.dropList);
  //   }
  // }
  // dropped(event: CdkDragDrop<Task[]>) {
  
  // }

  // isDropAllowed(drag: CdkDrag, drop: CdkDropList) {
  //   if (this.dragDropService.currentHoverDropListId == null) {
  //     return true;
  //   }
  //   return drop.id === this.dragDropService.currentHoverDropListId;
  // }

  // dragMoved1(event: CdkDragMove<Task>) {
  //   this.dragDropService.dragMoved(event);
  // }

  // dragReleased(event: CdkDragRelease) {
  //   this.dragDropService.dragReleased(event);
  // }

  deleteColumn(columnName:any)
  {
    this.apiserv.deleteColumn(columnName,this.projName).subscribe(data=>{
      console.log(data);
      this.getColumns(this.projName)
    },error=>{
      console.log(error);
    })
  }

  onClickEdit(column:Column){
      
    console.log("column Name"+column);
    // console.log(projName)
   
     this.getProjDet.editColName=column.columnName;
    const dialogRef = this.dialog.open(EditColumnDialogBoxComponent)
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
      this.getColumns(this.projName)  
    });
    
  // console.log(projName);
  
  //  this.api.deleteProject(projName).subscribe(x=>{
  //    console.log(x);
     
  //    this.getProjectDetails();
  //  },error=>{
  //   console.log(error);
    
  // }) 
   
 }
}
