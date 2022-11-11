import { Component,  OnInit } from '@angular/core';
import {  Router } from '@angular/router';
import { Project } from 'src/app/class/project';
import { ApiService } from 'src/app/services/api.service';
import { GetProjectDetailsService } from 'src/app/services/get-project-details.service';
import { KanbanService } from 'src/app/services/kanban.service';
import {NgxPaginationModule} from 'ngx-pagination';
import { BoardNavigationService } from 'src/app/services/board-navigation.service';
import { MatDialog } from '@angular/material/dialog';
import { EditProjectDialogBoxComponent } from '../edit-project-dialog-box/edit-project-dialog-box.component';

@Component({
  selector: 'app-projects-dashboard',
  templateUrl: './projects-dashboard.component.html',
  styleUrls: ['./projects-dashboard.component.css']
})
export class ProjectsDashboardComponent implements OnInit {
  
  p:any;   //for navigation
  projName!:string
  projects:Project[]=[];
  projNames!:any
  proj!:Project
  allProjects:any 
  ngOnInit(): void {
    this.getProjectDetails();
   // this.getAllProj()
  }
  constructor(private service:KanbanService,private router:Router,private api:ApiService,public dialog: MatDialog,
    private boardNavServ:BoardNavigationService,private getProjServ:GetProjectDetailsService) {
  

      // this.projNames= this.router.getCurrentNavigation()?.extras.state 
      // try
      // {
      // this.proj=this.projNames.x;
      //   console.log(this.proj);
        
      // }
      // catch(error){
      //   console.error(error);
      // }
      // this.getBgImg(this.proj)
  }
  
    
     getProjectDetails(){
      this.service.getProjects(localStorage.getItem('userEmail'))
      .subscribe({
        next:(res)=>{
          console.log(res);
          this.projects=res;
       
        },
        error:(err)=>{
          alert("Error while fetching data!!")
        }
      })
     }
    
     onClickDelete(projName:any){
    
      console.log(projName);
      
       this.api.deleteProject(projName).subscribe(x=>{
         console.log(x);
         
         this.getProjectDetails();
       },error=>{
        console.log(error);
        
      }) 
     } 

     onClickEdit(project:Project){
      
        console.log("column Name"+project);
        // console.log(projName)
       
         this.getProjServ.editProjName=project.projectName;
        const dialogRef = this.dialog.open(EditProjectDialogBoxComponent)
        dialogRef.afterClosed().subscribe(result => {
          console.log(`Dialog result: ${result}`);
          this.getProjectDetails()  
        });
        
      // console.log(projName);
      
      //  this.api.deleteProject(projName).subscribe(x=>{
      //    console.log(x);
         
      //    this.getProjectDetails();
      //  },error=>{
      //   console.log(error);
        
      // }) 
       
     }
    
     projBgImg!:String
    projectName!:String
     getBgImg(y:Project){
      console.log(y)
      this.projBgImg=y.projectBgImage;
      this.projectName=y.projectName
      this.boardNavServ.projName=y
      console.log(this.projectName);
      this.getProjServ.projectName=this.projectName
      let url="/admin/home1/showboard/:"+this.projectName;
      console.log(url)
    //  this.router.navigate(['/admin/home1/showboard/'+this.projectName])
      const state={y}
      console.log(state.y);
      
      this.router.navigate(["/admin/home1/showboard/:"],{ state })
      // this.reloadComponent(false,url) 
      
      // this.reloadComponent(true,x.projectName);
    }

    getAllProj()
  {
    this.api.getAllProj().subscribe(x=>{
      console.log(x);
      this.allProjects=x;
    },
    error=>{
      console.log(error)
    })
  }
}
