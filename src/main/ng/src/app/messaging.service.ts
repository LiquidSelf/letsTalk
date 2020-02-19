import { Injectable, OnInit, OnDestroy } from '@angular/core';
import { Observable, of } from 'rxjs';
import { webSocket } from 'rxjs/webSocket';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { throwIfEmpty, mergeMap, retry } from 'rxjs/operators';
import { WebSocketSubject, WebSocketSubjectConfig } from 'rxjs/webSocket';
import { Observer } from 'rxjs/internal/types';
import { Subscription } from 'rxjs/internal/Subscription';
import {AuthService} from "./auth.service";
import {Message} from "./chat-page/Message";
import {AppMessageService, MessageColor} from "./app-message.service";
import {WebSocketMessage} from "./dto/WebSocketMessage";

@Injectable({
  providedIn: 'root'
})
export class MessagingService implements Observer<WebSocketMessage>, OnDestroy{
  private origin_url: string = window.location.origin;
  private subject   : WebSocketSubject<WebSocketMessage>;

  private messListeners:MessListener[] = new Array();

  private intervalId: number;

  private isWaiting: boolean = false;


  next = function (mess) {
    this.onNewMessage(mess, true);
  };
  error= function (err) {
    console.error("error on MessagingService ws", err);
  };
  complete = function () {
  };

  constructor(
    private http: HttpClient,
    private auth: AuthService,
    private appMess: AppMessageService,
  )
  {
    this.messListeners = new Array();
    this.intervalId = window.setInterval(()=>{
      if(this.isConnected()) {this.sendPing()}
      else if (this.auth.getToken()){this.reconnect();}
    }, 1000);
  }

  public reconnect(){

    if(this.isWaiting) return;

    this.isWaiting = true;

    this.getAllMessages().subscribe(
      (result:Message[])=>{
        this.onNewMessage(result, false);
        this.initWs(this.auth.getToken());
        this.isWaiting = false;
      },
      (ex: any)=>{
        if(ex.status == 403) this.appMess.showUnauthorized();
        else                 console.log('unexpected exception on get all msgs', ex);

        this.isWaiting = false;
      }
    )
  }

  public initWs(token:string){
    let wsUrl = this.origin_url.replace('http', 'ws')+"/ws_api/chat/web_socket";

    this.subject = webSocket({
      url: wsUrl,
      closeObserver: {
        next: (close_event) => {
          this.subject.closed = true;
          console.log('wsCloseEvent', close_event);
          if(close_event && close_event.code == 4500){
            this.auth.putToken(null);
          }
        }
      },

      openObserver: {
        next: (open_event) => {
          this.subject.closed = false;
        }
      },

      closingObserver:{
        next: () => {
          console.log('closingObserver');
        }
      },
    });

    this.subject.subscribe(this);
  }

  getAllMessages():Observable<Message[]>{
    return this.http.get<Message[]>(this.origin_url + '/api/chat/getMessages');
  }

  onNewMessage(mess: Message[] | Message, async:boolean){
    this.messListeners.forEach((listener:MessListener)=> {
      listener.onMessage(mess, async);
    });
  }

  sendPing(){
    var ws_mes:WebSocketMessage = new WebSocketMessage(this.auth.getToken());
    ws_mes.isPing = true;
    this.subject.next(ws_mes);
  }

  sendMessage(message: string){
    let msg: Message = new Message(message);
    let ws_mes:WebSocketMessage = new WebSocketMessage(this.auth.getToken());
    ws_mes.data = msg;
    this.subject.next(ws_mes);
  }


  isConnected(){
    return (this.subject && !this.subject.closed);
  }

  addMessListener(messListener:MessListener){
    if(messListener)
      this.messListeners.push(messListener);
  }


  ngOnDestroy(): void {
    this.subject.unsubscribe();
    window.clearInterval(this.intervalId);
    this.messListeners = new Array();
  }
}


export interface MessListener {
  onMessage(mess: Message[] | Message, async:boolean) : void;
}
