import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProjectMember } from 'src/app/class/project-member';
import { KanbanService } from 'src/app/services/kanban.service';

@Component({
  selector: 'app-add-new-person',
  templateUrl: './add-new-person.component.html',
  styleUrls: ['./add-new-person.component.css']
})
export class AddNewPersonComponent implements OnInit {
  addPersonForm= new FormGroup({
    memberName:new FormControl('',[Validators.required]),
    memberEmail:new FormControl('',[Validators.required,Validators.email]),
   
  })
  constructor(private service:KanbanService,private route:Router) { }

  ngOnInit(): void {
        

  }
  msg:any;
  member:ProjectMember=new ProjectMember();

  public addPerson(){
    if ((this.addPersonForm.value.memberName != '' && this.addPersonForm.value.memberEmail != '') &&
     (this.addPersonForm.value.memberName != null && this.addPersonForm.value.memberEmail != null)) {
      
      alert("added new person to account successfully")
      this.route.navigateByUrl('/admin/people');
      
      console.log(this.addPersonForm.value)
     }
    else{
      alert("Either of the fields should not be empty")
    }
    
    
    this.member.memberName=this.addPersonForm.value.memberName;
    this.member.memberEmail=this.addPersonForm.value.memberEmail;
    
     this.service.addMember(localStorage.getItem('userEmail'),this.member).subscribe(x=>console.log('data saved'))
}
public cancel(){
  this.route.navigateByUrl('/admin/people')
}

}
