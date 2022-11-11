import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { KanbanService } from 'src/app/services/kanban.service';

@Component({
  selector: 'app-check-otp',
  templateUrl: './check-otp.component.html',
  styleUrls: ['./check-otp.component.css']
})
export class CheckOtpComponent implements OnInit {
  checkOtpForm:FormGroup
  constructor(private service:KanbanService,private router:Router){ 
    this.checkOtpForm=new FormGroup({
      otp:new FormControl('',[Validators.required])
     })
  }

  ngOnInit(): void {
  }
  home(){
    this.router.navigateByUrl('home-page');
  }

  checkOtp()
  {
    if(this.checkOtpForm.value.otp === this.service.checkOtp()){
      this.router.navigate(['.//set-new-password'])
      
    }
    else{
      alert('Invalid OTP!')
    }
  }
}
