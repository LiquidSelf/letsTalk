import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { MessagingService } from '../messaging.service';
import { Component, AfterViewChecked, ElementRef, ViewChild, OnInit} from '@angular/core'
import { AuthService } from "../auth.service";


@Component({
  selector: 'app-chat-page',
  templateUrl: './chat-page.component.html',
  styleUrls: ['./chat-page.component.css']
})
export class ChatPageComponent implements OnInit {

  @ViewChild('scroll', {static: false})
  private myScrollContainer: ElementRef;

  private message:string;

  constructor
  (
    private auth: AuthService,
    private route: ActivatedRoute,
    private location: Location,
    private mess_serv: MessagingService,
  )
  {

     let callback = {
       next:(tiket:string) => this.initConnection(tiket),
       error:(error:any)   => this.handleError(error)
     }

     if(auth.getSub())
     this.auth.wsTempTiket(callback)
     else
     location.back();
  }

  initConnection(tiket:string){
    if(!tiket || tiket === 'null'){
      this.handleError(null)
      return;
    }
    else
    this.mess_serv.init(tiket);
  }

  handleError(error){
    if(error && error.status === 403){
      console.log("$)$))$)343")
    }
  }

  ngOnInit() {
    this.scrollToBottom();
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  scrollToBottom(): void {
    try {
      this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
    } catch(err) { }
  }

  goBack(): void {
    this.location.back();
    this.mess_serv.closeWs();
  }

  sendMessage(message:string): void{
    if(message){
        this.mess_serv.sendMessage(message);
        this.message = '';
    }
  }

}
