import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';

import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthGuard } from './guards/auth.guard';
import { AppComponent } from './app.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { CheckOtpComponent } from './components/check-otp/check-otp.component'; 
import { SetNewPasswordComponent } from './components/set-new-password/set-new-password.component';


const routes: Routes = [
  {
    path:'login',
    component:LoginComponent
  },
  {
    path:'forgot-password',
    component:ForgotPasswordComponent
  },
  {
    path:'',
    // component:RegisterComponent
    redirectTo:'home-page',
    pathMatch:'full'

  },
  {
    path:'home-page',
    component:HomePageComponent
  },
  {
    path:'check-otp',
    component:CheckOtpComponent
  },
  {
    path:'set-new-password',
    component:SetNewPasswordComponent
  },
  {
  path:'register',
  component:RegisterComponent
  },
  {
    path:'admin',
    canActivate:[AuthGuard],
    loadChildren:() => import('./modules/admin/admin.module').then((m)=>m.AdminModule),  //lazy loading.if user visit admin route,
                                                                                        // then this module is loaded.i.e.it loads on demand
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
