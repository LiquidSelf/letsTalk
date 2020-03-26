import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import {MessageType, MessagingService, MessListener} from '../messaging.service';
import { Router } from '@angular/router';
import { AuthService } from "../auth.service";
import { AppMessageService } from "../app-message.service";
import {FeedMessage} from "../dto/feed/FeedMessage";
import {DTO} from "../dto/DTO";
import {NewFeedDialogComponent} from "../new-feed-dialog/new-feed-dialog.component";
import { MatDialog } from '@angular/material/dialog';
import {Message} from "../chat-page/Message";

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit, OnDestroy, MessListener {

  feedMsgs: FeedMessage[] = [];
  isLoading: boolean = false;
  displayOrder: boolean = false;

  constructor(
    public auth: AuthService,
    private route: ActivatedRoute,
    private http: HttpClient,
    private mess_serv: MessagingService,
    private router: Router,
    public appMsg: AppMessageService,
    public dialog: MatDialog
  ) {
    let disOr = localStorage.getItem("FeedComponent.displayOrder");
    if(disOr === 'true') this.displayOrder = true;
    this.feelFeed()
  }

  feelFeed(){
    this.reload();
  }

  reload(){
    let params = new HttpParams().set("fromId", "322");

    this.isLoading = true;

    this.http.get<DTO<FeedMessage[]>>(
      AuthService.origin_url + '/api/feed/',
      {params: params}
    ).subscribe(
      (result:DTO<FeedMessage[]>)=>{
        this.feedMsgs = result.data;
      },
      (ex: any)=>{
        // if(ex.status == 403) this.appMess.showUnauthorized();
        // else                 console.log('unexpected exception on get all msgs', ex);

      },
      ()=>{
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.mess_serv.addMessListener(this);
  }


  ngOnDestroy(): void {
    this.mess_serv.addMessListener(this);
  }


  onMessage(mess: Message[] | Message, async: boolean): void {
    this.reload();
  }

  interestedIN(): MessageType[] {
    return [MessageType._FEED];
  }

  newRecord(){
    if(!this.auth.user) return;

    let dialogObject = {};

    const dialog = this.dialog.open(NewFeedDialogComponent, {
      width: '800px',
      data: dialogObject
    }).afterClosed().subscribe(()=>{
        this.reload();
    })

  }

  chgStyle(event){
    this.displayOrder = !this.displayOrder;
    localStorage.setItem("FeedComponent.displayOrder", this.displayOrder +'');
  }
}
