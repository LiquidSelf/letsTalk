import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MessagingService } from '../messaging.service';
import { Router } from '@angular/router';
import { AuthService } from "../auth.service";
import { AppMessageService } from "../app-message.service";
import { FriendlyMessage } from "../app-message.service";


@Component({
  selector: 'app-wellcome-page',
  templateUrl: './wellcome-page.component.html',
  styleUrls: ['./wellcome-page.component.css']
})
export class WellcomePageComponent implements OnInit {

  constructor(
    private auth: AuthService,
    private route: ActivatedRoute,
    private http: HttpClient,
    private mess_serv: MessagingService,
    private router: Router,
    private appMsg: AppMessageService,
  ) {
  }
  ngOnInit() {

  }

  test(){
    this.appMsg.showMessage("test pressed!");
  }
}
