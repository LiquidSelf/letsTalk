import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MessagingService } from '../messaging.service';
import { Router } from '@angular/router';
import { AuthService } from "../auth.service";
import { AppMessageService } from "../app-message.service";
import {FeedMessage} from "../dto/FeedMessage";

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

  feedMsgs: FeedMessage[] = [];

  constructor(
    public auth: AuthService,
    private route: ActivatedRoute,
    private http: HttpClient,
    private mess_serv: MessagingService,
    private router: Router,
    public appMsg: AppMessageService
  ) {

    this.feedMsgs = [
      new FeedMessage(),
      new FeedMessage(),
      new FeedMessage(),
      new FeedMessage(),
      new FeedMessage(),
      new FeedMessage(),
      new FeedMessage(),
      new FeedMessage(),
      new FeedMessage(),
      new FeedMessage(),
    ]

  }

  ngOnInit(): void {
  }

}
