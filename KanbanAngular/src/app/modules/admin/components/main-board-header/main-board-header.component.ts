import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { map, Observable, shareReplay } from 'rxjs';
import { Project } from 'src/app/class/project';
import { User } from 'src/app/class/user';
import { ApiService } from 'src/app/services/api.service';
import { BoardNavigationService } from 'src/app/services/board-navigation.service';
import { GetProjectDetailsService } from 'src/app/services/get-project-details.service';

@Component({
  selector: 'app-main-board-header',
  templateUrl: './main-board-header.component.html',
  styleUrls: ['./main-board-header.component.css']
})
export class MainBoardHeaderComponent implements OnInit {

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );
    public user:User=new User;
    userName:string="";
    projName!:any
    allProjects:any  
    proj!:Project
   
   // userName:any=window.localStorage.getItem('userName');
    userEmail:any=window.localStorage.getItem('userEmail');
  constructor(private breakpointObserver: BreakpointObserver,private apiServ:ApiService,private boardNavServ:BoardNavigationService
    ,private router:Router,private activatedRoute:ActivatedRoute,private getProjServ:GetProjectDetailsService) {
      this.projName= this.router.getCurrentNavigation()?.extras.state 
      try
      {
          
      this.proj=this.projName.y;
        console.log(this.proj);
        
      }
      catch(error){
        console.error(error);
      }
      this.getBgImg(this.proj)






      // activatedRoute.params.subscribe(data=>{
      //   // this.projName=data['projectName'];
        // console.log(data);
        // this.projName=data;
        // this.apiServ;
        // console.log(this.projName);
        // this.getBgImg(data)
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
    this.router.navigate(['/admin/home1/showboard/'+this.projectName])
    // this.reloadComponent(false,url) 
    
    // this.reloadComponent(true,x.projectName);
  }

  reloadComponent(self:boolean,urlToNavigateTo ?:string){
    //skipLocationChange:true means dont update the url to / when navigating
   console.log("Current route I am on:",this.router.url);
   const url=self ? this.router.url :urlToNavigateTo;
   this.router.navigateByUrl('/',{skipLocationChange:true}).then(()=>{
     this.router.navigate([`/admin/${url}`]).then(()=>{
       console.log(`After navigation I am on:${this.router.url}`)
     })
   })
  }
  getAllProj()
  {
    this.apiServ.getAllProj().subscribe(x=>{
      console.log(x);
      this.allProjects=x;
    },
    error=>{
      console.log(error)
    })
  }

  ngOnInit(): void {
    this.getAllProj()
    this.apiServ.getUser(localStorage.getItem('userEmail')).subscribe(x=>{
      this.userName=x.userName;
    })

  }


}
