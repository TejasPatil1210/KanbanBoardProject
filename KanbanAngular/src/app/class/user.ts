import { Project } from "./project";
import { ProjectMember } from "./project-member";

export class User {
    userName:any;
    userEmail:any;
    password:any;
    projectLists:Project[];
    projectMemberList:ProjectMember[];
    constructor(){
        this.userName="";
        this.userEmail="";
        this.password="";
        this.projectLists=[];
        this.projectMemberList=[];
    }
}
