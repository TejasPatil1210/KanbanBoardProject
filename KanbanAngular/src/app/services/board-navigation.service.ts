import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BoardNavigationService {

  constructor() { }
  projName:any;
  
  
  getprojName()
  {
   console.log(this.projName)
    return this.projName;
  }
}
