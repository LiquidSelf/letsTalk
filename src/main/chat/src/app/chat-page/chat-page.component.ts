import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { MessagingService } from '../messaging.service';
import { Component, AfterViewChecked, ElementRef, ViewChild, OnInit} from '@angular/core'
@Component({
  selector: 'app-chat-page',
  templateUrl: './chat-page.component.html',
  styleUrls: ['./chat-page.component.css']
})
export class ChatPageComponent implements OnInit {

  @ViewChild('scroll', {static: false})
  private myScrollContainer: ElementRef;

  private message:string;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private mess_serv: MessagingService
  ) {}

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
