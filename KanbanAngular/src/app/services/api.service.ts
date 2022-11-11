import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Column } from '../class/column';
import { Project } from '../class/project';
import { ProjectMember } from '../class/project-member';
import { User } from '../class/user';
import { KanbanService } from './kanban.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http:HttpClient,private service:KanbanService) { }

  createProjApi="http://localhost:9000/api/v2/kanban-service/create-project"
  updateProjApi="http://localhost:9000/api/v2/kanban-service/update-project"
  createColApi="http://localhost:9000/api/v2/kanban-service/create-column"
  updateColApi="http://localhost:9000/api/v2/kanban-service/update-column"
  getAllProjectsApi="http://localhost:9000/api/v2/kanban-service/projects"
  getAllColumnsApi="http://localhost:9000/api/v2/kanban-service/columns"
  getAllTasksApi="http://localhost:9000/api/v2/kanban-service/tasks/TV-app4/Todo"
  createTaskApi="http://localhost:9000/api/v2/kanban-service/create-task"
  updateTaskApi="http://localhost:9000/api/v2/kanban-service/update-task"
  deleteTaskApi="http://localhost:9000/api/v2/kanban-service/delete-task"
  deleteTaskFromAnyColApi="http://localhost:9000/api/v2/kanban-service/delete-task-from-any-col"
  projectMemberApi="http://localhost:9000/api/v2/kanban-service/project-members"
  getTaskApi="http://localhost:9000/api/v2/kanban-service/task"
  getProjectApi="http://localhost:9000/api/v2/kanban-service/project"
  getColumnApi="http://localhost:9000/api/v2/kanban-service/column"
  deleteProjectApi="http://localhost:9000/api/v2/kanban-service/delete-project"
  deleteProjectMemberApi="http://localhost:9000/api/v2/kanban-service/delete-project-member"
  createProject(projData:Project)
  {
   // console.log(this.service.currentuser.userName);
    
    return this.http.post<Project>(this.createProjApi+"/"+localStorage.getItem('userEmail'),projData)
  }
  updateProject(projData:Project)
  {
    return this.http.put<Project>(this.updateProjApi+"/"+localStorage.getItem('userEmail'),projData)
  }
  createColumn(column:Column,projectName:any)
  {

    return this.http.post<Column>(`${this.createColApi+"/"+localStorage.getItem('userEmail')}/${projectName}`,column)
  }
  updateColumn(column:Column,projectName:any)
  {
    return this.http.put<Column>(`${this.updateColApi+"/"+localStorage.getItem('userEmail')}/${projectName}`,column)
  }

  getAllCols(projName:String)
  {
    console.log("hiiiiiiiiiii")
    console.log(projName);
    
    return this.http.get<Column[]>(`${this.getAllColumnsApi+"/"+localStorage.getItem('userEmail')}/${projName}`)
  }

  getAllProj()
  {
    return this.http.get<Project[]>(this.getAllProjectsApi+"/"+localStorage.getItem('userEmail'))
  }

  getAllTask()
  {
    return this.http.get<Task[]>(this.getAllTasksApi+"/"+localStorage.getItem('userEmail'))
  }

  createTask(projName:string,colName:string,data:Task)
  {
    console.log(colName);

    console.log(data);
    
    
    return this.http.post<Task>(`${this.createTaskApi+"/"+localStorage.getItem('userEmail')}/${projName}/${colName}`,data)
  }

  updateTask(projName:string,colName:string,data:Task){
    return this.http.put<Task>(`${this.updateTaskApi+"/"+localStorage.getItem('userEmail')}/${projName}/${colName}`,data)
  }
  deleteTask(projName:string,colName:string,taskId:string)
  {
    console.log(colName+ " "+taskId);
    
    return this.http.delete(`${this.deleteTaskApi+"/"+localStorage.getItem('userEmail')}/${projName}/${colName}/${taskId}`)
  }

  deleteTaskFromAnyCol(projName:string,colName:string,taskId:string)
  {
    console.log(colName+ " "+taskId);
    
    return this.http.delete(`${this.deleteTaskFromAnyColApi+"/"+localStorage.getItem('userEmail')}/${projName}/${colName}/${taskId}`)
  }
  getProject(projName:string)
  {
    return this.http.get<Project>(`${this.getProjectApi+"/"+localStorage.getItem('userEmail')}/${projName}`)   
  }

  getColumn(projName:string,colName:string)
  {
    return this.http.get<Column>(`${this.getColumnApi+"/"+localStorage.getItem('userEmail')}/${projName}/${colName}`)   
  }
  getTask(projName:string,colName:string,taskId:string)
  {
    return this.http.get<Task>(`${this.getTaskApi+"/"+localStorage.getItem('userEmail')}/${projName}/${colName}/${taskId}`)
  }
  getprojectmember()
  {
    // let url=this.projectMemberApi+this.currentuser.userEmail;
    return this.http.get<ProjectMember[]>(this.projectMemberApi+"/"+localStorage.getItem('userEmail'))
  }

  getuserUrl= 'http://localhost:9000/api/v2/kanban-service/find-user/';
  getUser(email:any){
    return this.http.get<User>(this.getuserUrl+email);
  }

  deleteProject(projName:string)
  {
    console.log(projName);
    
    return this.http.delete(`${this.deleteProjectApi+"/"+localStorage.getItem('userEmail')}/${projName}`)
  }

  deleteProjectMember(memName:string){
    return this.http.delete(`${this.deleteProjectMemberApi+"/"+localStorage.getItem('userEmail')}/${memName}`)
  }

  deleteColApi="http://localhost:9000/api/v2/kanban-service/delete-column/"
  deleteColumn(columnName:any,projectName:any)
  {
    return this.http.delete(`${this.deleteColApi+"/"+localStorage.getItem('userEmail')}/${projectName}/${columnName}`)
  }
}
