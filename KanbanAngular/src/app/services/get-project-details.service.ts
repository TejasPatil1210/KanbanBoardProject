import { Injectable } from '@angular/core';
import { Project } from '../class/project';

@Injectable({
  providedIn: 'root'
})
export class GetProjectDetailsService {

  constructor() { }

  projectName:any
  columnName:any
  isEditEnabled:boolean=false
  taskId:any
  getProjName()
  {
    return this.projectName;
  }

  getColName()
  {
    return this.columnName;
  }

  getEditEnabled()
  {
    return this.isEditEnabled;
  }

  getTaskId()
  {
    return this.taskId;
  }

  editProjName:any

  getEditProjName()
  {
    return this.editProjName;
  }

  editColName:any
  
  getEditColName()
  {
    return this.editColName;
  }

  editProjData:any

  getEditProjData()
  {
    return this.editProjData;
  }

  editColData:any

  getEditColData()
  {
    return this.editColData;
  }

}
