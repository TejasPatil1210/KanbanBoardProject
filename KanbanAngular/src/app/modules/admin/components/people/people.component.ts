import { Component, OnInit,ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ProjectMember } from 'src/app/class/project-member';
import { KanbanService } from 'src/app/services/kanban.service';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-people',
  templateUrl: './people.component.html',
  styleUrls: ['./people.component.css']
})
export class PeopleComponent implements OnInit {
  displayedColumns: string[] = ['memberName', 'memberEmail','action'];
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  constructor(private service:KanbanService,private route:Router,private api:ApiService) { }
  name:any;
  ngOnInit(): void {
    this.service.refreshNeeded
    .subscribe(() => {
      this.getAllMembers();
    })
    this.getAllMembers();
    
  }
  project:ProjectMember[]=[];
  addPeople(){
    
      this.route.navigate(["/admin/addperson"]);
      
     }
   
     getAllMembers(){
      this.service.getMembers(localStorage.getItem('userEmail'))
      .subscribe({
        next:(res)=>{
          console.log(res);
          this.dataSource = new MatTableDataSource(res);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
         // this.project=res;
    

        },
        error:(err)=>{
          alert("Error while fetching data!!")
        }
      })
     }
     applyFilter(event: Event) {
      const filterValue = (event.target as HTMLInputElement).value;
      this.dataSource.filter = filterValue.trim().toLowerCase();
  
      if (this.dataSource.paginator) {
        this.dataSource.paginator.firstPage();
      }
    }
  

    deleteMember(memberEmail:any){
      console.log(memberEmail);
      
      this.api.deleteProjectMember(memberEmail).subscribe(x=>{
        console.log(x);
        
        this.getAllMembers();
      },error=>{
       console.log(error);
       
     }) 
      
    } 
    }



