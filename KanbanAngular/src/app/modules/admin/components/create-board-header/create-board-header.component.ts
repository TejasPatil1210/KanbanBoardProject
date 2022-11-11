import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { map, Observable, shareReplay } from 'rxjs';

@Component({
  selector: 'app-create-board-header',
  templateUrl: './create-board-header.component.html',
  styleUrls: ['./create-board-header.component.css']
})
export class CreateBoardHeaderComponent {

 
  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(private breakpointObserver: BreakpointObserver,private router:Router) {}
   navigate(){
    this.router.navigateByUrl('/admin/home-dashboard')
   }
}
