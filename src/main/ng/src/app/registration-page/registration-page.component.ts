import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService} from "../auth.service";
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { AppMessageService } from "../app-message.service";
import { FriendlyMessage, MessageColor } from "../app-message.service";

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit {

  userForm: any;

  constructor( private auth: AuthService,
               private http: HttpClient,
               private router: Router,
               private route: ActivatedRoute,
               private formBuilder: FormBuilder,
               private appMsg: AppMessageService,
  ) {

  }

  ngOnInit() {
    this.userForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern('^[a-zA-Z]+$')]],
      password: ['', [Validators.required]],
      passRepeat: ['', [Validators.required]],
    });
  }


  onSubmit(){
      if(this.userForm.valid)
        this.auth.registrate(this.userForm.value,
          (msg:string)=>{
          if(msg) this.appMsg.showMessage(msg, 5000, MessageColor._RED);
          else    this.appMsg.showMessage("registration failed!", 5000, MessageColor._RED);
        });
      else
        this.appMsg.showMessage("validation error", 2000, MessageColor._YELLOW);
  }
}
