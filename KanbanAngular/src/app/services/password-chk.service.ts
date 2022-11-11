import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PasswordChkService {
  password:any;
  constructor() { }
  getPassword()
  {
    return this.password;
  }
}
