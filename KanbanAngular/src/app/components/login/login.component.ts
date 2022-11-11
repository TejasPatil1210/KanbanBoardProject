import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { map, Observable, shareReplay } from 'rxjs';
import { User } from 'src/app/class/user';
import { KanbanService } from 'src/app/services/kanban.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  data?: boolean;
  loginForm: FormGroup = new FormGroup({
    userEmail: new FormControl('', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
    password: new FormControl('', [Validators.required]),
  });
  error: string[] = [];
  constructor(private service: KanbanService, private router: Router, private breakpointObserver: BreakpointObserver) { }
  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );
  ngOnInit(): void {
    if (this.service.isLoggedIn()) {
      this.router.navigate(['admin']);
    }
  //  this.service.getAllUsers().subscribe(x => console.log(x));
   }
  reg() {
    console.log("reg");
    this.router.navigateByUrl('register');
  }
  home(){
    this.router.navigateByUrl('home-page');
  }
  user: User = {} as User

  onLogin() {

    if ((this.loginForm.value.userEmail != '' && this.loginForm.value.password != '') &&
      (this.loginForm.value.userEmail != null && this.loginForm.value.password != null)) {
      console.log("Form is ready to submit")
      this.user.password = this.loginForm.value.password;
      this.user.userEmail = this.loginForm.value.userEmail;
    //  this.service.currentuser={}as User
      this.service.authserver(this.user).subscribe((s: any) => {
        console.log(s.token);
  
        this.service.loginUser(s.token, this.user.userEmail);
        this.router.navigateByUrl('admin')
      },
        (err) => {
          console.log(err)
          this.data = false;
          alert("invalid credentials")
        })
      //  this.service.getUser(this.loginForm.value.userEmail).subscribe(s=>{this.service.currentuser=s},err=>{})
    }
    console.log(this.user.userEmail);
  }
}


  // onSubmit(): void {
  //   if (this.loginForm.valid) {
  //     this.service.login(this.loginForm.value).subscribe(
  //       (result) => {
  //         console.log(result);
  //         this.router.navigate(['login']);
  //       },
  //       (err: Error) => {
  //         alert(err.message);
  //       }
  //     );
  //   }
  // }



