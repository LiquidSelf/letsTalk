import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService} from "../auth.service";
import { ActivatedRoute } from '@angular/router';
import { AppMessageService } from "../app-message.service";
import { FriendlyMessage } from "../app-message.service";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  credentials = {
    username: '',
    password: ''
  };

  constructor(
    private auth: AuthService,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    private appMsg: AppMessageService,
  ) {
  }

  ngOnInit() {
  }

  onSubmit(){
    this.auth.authenticate(this.credentials, msg => {
      if(msg)
      this.appMsg.showMessage(msg);
      else
      this.appMsg.showMessage("auth failed");
    });
  }
}
