import { Injectable } from '@angular/core';
import { Message } from "./objects/Message";
import { Observable, of } from 'rxjs';
import { webSocket } from 'rxjs/webSocket';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { throwIfEmpty, mergeMap, retry } from 'rxjs/operators';
import { WebSocketSubject, WebSocketSubjectConfig } from 'rxjs/webSocket';
import { Observer } from 'rxjs/internal/types';
import { Subscription } from 'rxjs/internal/Subscription';

@Injectable({
  providedIn: 'root'
})
export class MessagingService implements Observer<Message>{

  private origin_url: string = window.location.origin;
  private subject   : WebSocketSubject<Message>;
  private messages  : Message[] = [];

  closed: boolean;

  next = function (mess:Message) {
    this.onMessage(mess);
  };
  error= function (err) {
    console.error("error on MessagingService ws", err);
  };
  complete = function () {
  };

  constructor(
    private http: HttpClient
  )
  {
  }

  public init(){
    this.getAllMessages().subscribe(
      (result:Message[])=>{
        this.messages = result;
      }
    );

    this.subject = webSocket({
      url: this.origin_url.replace('http', 'ws')+"/api/chat/web_socket",
      closeObserver: {
        next: (close_event) => {
        }
      },

      openObserver: {
        next: (open_event) => {
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

  getAllMessages():Observable<Message[]>{
    return this.http.get<Message[]>(this.origin_url + '/api/chat/getMessages');
  }

  onMessage(mess: Message){
    this.messages.push(mess);
  }

  sendMessage(message: string){
    var mes:Message = new Message(message);
    this.subject.next(mes);
  }

}
