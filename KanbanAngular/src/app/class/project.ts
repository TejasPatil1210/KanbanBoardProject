import { Column } from "./column";

export class Project {
    projectId:any;
    projectName:any;
    projectBgImage:any;
    columnList:Column[];

    constructor(){
        this.projectId="";
        this.projectName="";
        this.projectBgImage="";
        this.columnList=[];
    }
}
