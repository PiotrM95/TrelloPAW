import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = "";
  password = "";
  comunicate = "good username";
  allowLogin = false;

  constructor(private router: Router) { }

  ngOnInit() {
  }

  isUsernameAndPasswordFilled(){
    if(this.username.length > 2 && this.password.length > 2){
      this.allowLogin = true;
    } else {
      this.allowLogin = false;
    }
  }

  onInputsUpdate(){
    this.isUsernameAndPasswordFilled();
  }

  logIn(){
    this.router.navigate(['/home']);
  }

}
