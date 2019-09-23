import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService} from "../auth.service";
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  errorMessage:string;

  credentials = {
    username: '',
    password: ''
  };

  constructor(
    private auth: AuthService,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.errorMessage = null;
  }

  ngOnInit() {
  }

  onSubmit(){
    this.auth.authenticate(this.credentials, (response)=>{
      if(response)
      this.errorMessage = response;
    });
  }
}
