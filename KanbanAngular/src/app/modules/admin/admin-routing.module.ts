import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddNewPersonComponent } from './components/add-new-person/add-new-person.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { BoardDetailsComponent } from './components/board-details/board-details.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { CreateBoardHeaderComponent } from './components/create-board-header/create-board-header.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { HomeDashboardComponent } from './components/home-dashboard/home-dashboard.component';
import { MainBoardDashboardComponent } from './components/main-board-dashboard/main-board-dashboard.component';
import { MainBoardHeaderComponent } from './components/main-board-header/main-board-header.component';
import { MyProfileComponent } from './components/my-profile/my-profile.component';
import { PeopleComponent } from './components/people/people.component';

const routes: Routes = [
  {
    
      path:"home",
      component:CreateBoardHeaderComponent,
      children:[
        {
          path:"",
          component:BoardDetailsComponent,
        },
      ]
    },
    {
      path:"home1",
      component:MainBoardHeaderComponent,
      children:[
        {
          path:"showboard/:projectName",
          component:MainBoardDashboardComponent,
        },
      ]
    },
    {
      path:"home1/showboard/:projectName/ ",
      redirectTo:"home",
      pathMatch:'full'
    },
    // {
    //   path:"",
    //   redirectTo:"home",
    //   pathMatch:'full'
    // },
    {
    path:'',
    component:AdminDashboardComponent,
    children:[
      {
        path:'home-dashboard',component:HomeDashboardComponent
      },
      {
        path:'people',component:PeopleComponent
      },
      {
        path:'addperson',component:AddNewPersonComponent
      },
      {
        path:'my-profile',component:MyProfileComponent
      },
      {
        path:'edit-profile',component:EditProfileComponent
      },
      {
        path:'change-password',component:ChangePasswordComponent
      },
      {
        path:'',redirectTo:'/admin/home-dashboard',pathMatch:'full'
      }
      
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
