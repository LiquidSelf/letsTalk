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
  private user      : any;
  private messages  : Message[] = [];

  closed: boolean;
  next: (value: Message) => void;
  error: (err: any) => void;
  complete: () => void;

  constructor(
    private http: HttpClient
  )
  {
    this.http.get<String>(this.origin_url+'/whoAmI')
      .subscribe(
        (value:any)=>{
          this.user = value;
          this.initWs();
          },
        (error:any)=>{
          MessagingService.redirectLogout();
        },
        ()=>{}
        )
  }

  public static redirectLogout(){
      document.location.href = 'logout';
  }

  private initWs(){
    this.next = (value:Message)=>{this.onMessage(value)}
    this.error = (err: any) => {console.log('error', err)}
    this.complete = () => {console.log('complete')}

    this.subject = webSocket({
      url: this.origin_url.replace('http', 'ws')+"/ws",
      closeObserver: {
        next: (close_event) => {
          console.log(this.subject._output);
          if(this.subject._output.observers.length === 0)
            this.subject.subscribe(this);
        }
      },

      openObserver: {
        next: (open_event) => {
          console.log("opn")
        }
      },

      closingObserver:{
        next: () => {
          window.alert('closin');
        }
      },
    })


    this.subject.subscribe(this);

    console.log(this.subject._output);

    this.getAllMessages().subscribe(
      (result:Message[])=>{
        this.messages = result;
      }
    );
  }

  getAllMessages():Observable<Message[]>{
    return this.http.get<Message[]>(this.origin_url + '/getMessages');
  }

  onMessage(mess: Message){
    this.messages.push(mess);
  }

  sendMessage(message: string){
    var mes:Message = new Message(this.user.name, message);
    this.subject.next(mes);
  }

}
