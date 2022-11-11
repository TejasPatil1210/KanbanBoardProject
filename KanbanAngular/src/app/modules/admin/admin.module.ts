import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { HeaderComponent } from './components/header/header.component';
import { HomeDashboardComponent } from './components/home-dashboard/home-dashboard.component';
import { PeopleComponent } from './components/people/people.component';
import { AddNewPersonComponent } from './components/add-new-person/add-new-person.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatGridListModule } from '@angular/material/grid-list';
import {MatDialogModule} from '@angular/material/dialog';
import { MyProfileComponent } from './components/my-profile/my-profile.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { ProjectsDashboardComponent } from './components/projects-dashboard/projects-dashboard.component';
import { BoardDetailsComponent } from './components/board-details/board-details.component';
import { ColumnDialogBoxComponent } from './components/column-dialog-box/column-dialog-box.component';
import { CreateBoardDetailsComponent } from './components/create-board-details/create-board-details.component';
import { CreateBoardHeaderComponent } from './components/create-board-header/create-board-header.component';
import { EditDialogBoxComponent } from './components/edit-dialog-box/edit-dialog-box.component';
import { MainBoardDashboardComponent } from './components/main-board-dashboard/main-board-dashboard.component';
import { MainBoardHeaderComponent } from './components/main-board-header/main-board-header.component';
import { TaskDialogBoxComponent } from './components/task-dialog-box/task-dialog-box.component';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import {NgxPaginationModule} from 'ngx-pagination';
import { EditColumnDialogBoxComponent } from './components/edit-column-dialog-box/edit-column-dialog-box.component';
import { EditProjectDialogBoxComponent } from './components/edit-project-dialog-box/edit-project-dialog-box.component';
import { AppModule } from 'src/app/app.module';
import { ImageDirective } from 'src/app/directives/image.directive';
import { UnderlineForProjectsDirective } from 'src/app/directives/underline-for-projects.directive';
import { UnderlineDirective } from 'src/app/directives/underline.directive';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';

@NgModule({
  declarations: [
    AdminDashboardComponent,
    HeaderComponent,
    HomeDashboardComponent,
    PeopleComponent,
    AddNewPersonComponent,
    MyProfileComponent,
    EditProfileComponent,
    ChangePasswordComponent,
    ProjectsDashboardComponent,
    BoardDetailsComponent,
    ColumnDialogBoxComponent,
    CreateBoardDetailsComponent,
    CreateBoardHeaderComponent,
    EditDialogBoxComponent,
    MainBoardDashboardComponent,
    MainBoardHeaderComponent,
    TaskDialogBoxComponent,
    EditColumnDialogBoxComponent,
    EditProjectDialogBoxComponent, 
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    MatSidenavModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatMenuModule,
    MatRadioModule,
    MatSelectModule,
    MatGridListModule,
    MatDialogModule,
    DragDropModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    NgxPaginationModule,
    ImageDirective,
    UnderlineForProjectsDirective,
    UnderlineDirective,
    MatDatepickerModule,
    MatNativeDateModule
  ]
})
export class AdminModule { }
