import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  userName: any;
  resetForm=new FormGroup({
    userName:new FormControl('',[Validators.required]),
    
   })
  constructor(public dailog: MatDialogRef<EditProfileComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
   // this.userName=this.data.userName
   console.log(this.data);
   this.resetForm.get('userName')?.setValue(this.data.userName)
  }
  submit(){
   
  }
}
