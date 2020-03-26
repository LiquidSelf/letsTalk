import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { MessageType, MessagingService, MessListener } from '../messaging.service';
import { Component, AfterViewChecked, ElementRef, ViewChild, OnInit, OnDestroy } from '@angular/core'
import { AuthService } from "../auth.service";
import {AppMessageService, MessageColor} from "../app-message.service";
import {Message} from "./Message";

@Component({
  selector: 'app-chat-page',
  templateUrl: './chat-page.component.html',
  styleUrls: ['./chat-page.component.css']
})
export class ChatPageComponent implements OnInit, MessListener{

  @ViewChild('scroll')
  public myScrollContainer: ElementRef;

  @ViewChild('textField')
  public textField: ElementRef;

  public messages  : Message[] = [];

  public message:string;
  public isShown: boolean;
  public isAutoScroll: boolean = true;
  public unreadMessages:Message[] = [];

  constructor
  (
    public auth: AuthService,
    private route: ActivatedRoute,
    private location: Location,
    public mess_serv: MessagingService,
    public appMess: AppMessageService,
  )
  {
  }

  ngOnInit() {
    this.mess_serv.addMessListener(this);
  }

  onUnreadClick(){
    if(!this.isShown){
      this.togleChat();
    }
    else this.scrollToBottom();
  }

  scrollToBottom(): void {
    try {
      window.setTimeout(
        ()=>{
          if(this.myScrollContainer && this.myScrollContainer.nativeElement) {
            let scrH = this.myScrollContainer.nativeElement.scrollHeight;
            if(scrH <= this.myScrollContainer.nativeElement.offsetHeight) this.unreadMessages = [];
            else
            this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
          }
        },
        50);
    } catch(err) {}
  }

  sendMessage(message:string): void{
    if(message){
      this.mess_serv.sendMessage(message);
      this.message = '';
    }
  }

  onScroll(){
    let element = this.myScrollContainer.nativeElement;

    if(Math.abs(element.scrollHeight - element.scrollTop - element.clientHeight) <= 15.0){
      this.unreadMessages = [];
      this.isAutoScroll = true;
    }else {
      this.isAutoScroll = false;
    }
  }

  onMessage(mess: Message[] | Message, async:boolean): void {
    if(!mess) return;

    if(!async) {
      this.messages = [];
      this.unreadMessages = [];
    }

    this.messages = this.messages.concat(mess);

    if(this.isAutoScroll && this.isShown) this.scrollToBottom();
    if(!this.isShown || !this.isAutoScroll){
      this.unreadMessages = this.unreadMessages.concat(mess);
    }
  }


  interestedIN(): MessageType[] {
    return [MessageType._CHAT];
  }

  togleChat(){
    this.isShown = !this.isShown;
    if(this.isShown){
      this.isAutoScroll = true;
      this.scrollToBottom();
      window.setTimeout(()=>{this.textField.nativeElement.focus()}, 500);
    }else{
      this.isAutoScroll = false;
    }
  }
}
