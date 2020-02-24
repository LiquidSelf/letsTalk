import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { MessagingService } from '../messaging.service';
import { Router } from '@angular/router';
import { AuthService } from "../auth.service";
import { AppMessageService } from "../app-message.service";
import {FeedMessage} from "../dto/FeedMessage";
import {DTO} from "../dto/DTO";
import {NewFeedDialogComponent} from "../new-feed-dialog/new-feed-dialog.component";
import { MatDialog } from '@angular/material/dialog';

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
    public appMsg: AppMessageService,
    public dialog: MatDialog
  ) {
    this.feelFeed()
  }

  feelFeed(){

    let params = new HttpParams().set("fromId", "322");

    this.http.get<DTO<FeedMessage[]>>(
      AuthService.origin_url + '/api/feed/',
      {params: params}
    ).subscribe(
      (result:DTO<FeedMessage[]>)=>{
        console.log(result.data);
        this.feedMsgs = result.data;
      },
      (ex: any)=>{
        // if(ex.status == 403) this.appMess.showUnauthorized();
        // else                 console.log('unexpected exception on get all msgs', ex);

      }
    );
  }

  ngOnInit(): void {
  }

  newRecord(){
    if(!this.auth.user) return;

    let dialogObject = {};

    const dialog = this.dialog.open(NewFeedDialogComponent, {
      width: '500px',
      data: dialogObject
    });
  }

}
