import { ProjectMember } from "./project-member";

export class Task {
    taskId:any;
    taskName:any;
    taskDescription:any;
    priority:any;
   cardType:any;
    dueDate:any;
    memberName:any;
    
    constructor(){
        this.taskId="";
        this.taskName="";
        this.taskDescription="";
        this.priority="";
        this.cardType="";
        this.dueDate="";
        this.memberName="";

    }
}
