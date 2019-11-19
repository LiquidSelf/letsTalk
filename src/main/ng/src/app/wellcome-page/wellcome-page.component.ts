import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MessagingService } from '../messaging.service';
import { Router } from '@angular/router';
import { AuthService } from "../auth.service";
import { AppErrorsService } from "../app-errors.service";
import { ErrorMessage } from "../app-errors.service";


@Component({
  selector: 'app-wellcome-page',
  templateUrl: './wellcome-page.component.html',
  styleUrls: ['./wellcome-page.component.css']
})
export class WellcomePageComponent implements OnInit {

  private chatShown:boolean = false;

  constructor(
    private auth: AuthService,
    private route: ActivatedRoute,
    private http: HttpClient,
    private mess_serv: MessagingService,
    private router: Router,
    private appErr: AppErrorsService,
  ) {

  }
  ngOnInit() {
  }

  test(){
    this.appErr.newMessage(new ErrorMessage("privet!"))
  }

  togleChat(){
    this.chatShown = !this.chatShown;
    if(this.chatShown){
      this.mess_serv.closeWs();
    }
  }
}
