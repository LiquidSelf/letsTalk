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
     if(auth.isAuthenticated())
     mess_serv.init();
     else
     location.back();
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
  }

  sendMessage(message:string): void{
    if(message){
        this.mess_serv.sendMessage(message);
        this.message = '';
    }
  }

}
