import { Injectable } from '@angular/core';
import { Message } from "./objects/Message";
import { Observable, of } from 'rxjs';
import { webSocket } from 'rxjs/webSocket';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { throwIfEmpty, mergeMap, retry } from 'rxjs/operators';
import { WebSocketSubject, WebSocketSubjectConfig } from 'rxjs/webSocket';
import { Observer } from 'rxjs/internal/types';
import { Subscription } from 'rxjs/internal/Subscription';
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class MessagingService implements Observer<Message>{

  private origin_url: string = window.location.origin;
  private subject   : WebSocketSubject<Message>;
  private messages  : Message[] = [];

  connected: boolean = false;

  next = function (mess:Message) {
    this.onMessage(mess);
  };
  error= function (err) {
    console.error("error on MessagingService ws", err);
  };
  complete = function () {
  };

  constructor(
    private http: HttpClient,
    private auth: AuthService
  )
  {
  }

  public init(tiket:string){
    this.getAllMessages().subscribe(
      (result:Message[])=>{
        this.messages = result;
      }
    );

    this.subject = webSocket({
      url: this.origin_url.replace('http', 'ws')+"/ws_api/chat/web_socket?tiket="+tiket,
      closeObserver: {
        next: (close_event) => {
          this.connected = false;
        }
      },

      openObserver: {
        next: (open_event) => {
          this.connected = true;
        }
      },

      closingObserver:{
        next: () => {
          console.log('clossi\'n');
        }
      },
    })

    this.subject.subscribe(this);
  }

  closeWs(){
    if(this.subject)
    this.subject.unsubscribe();
  }

  getAllMessages():Observable<Message[]>{
    return this.http.get<Message[]>(this.origin_url + '/api/chat/getMessages');
  }

  onMessage(mess: Message){
    this.messages.push(mess);
  }

  sendMessage(message: string){
    var mes:Message = new Message(message, this.auth.getSub());
    this.subject.next(mes);
  }

}
