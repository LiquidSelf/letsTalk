import { Component, OnInit, Input } from '@angular/core';
import { Renderer2 } from '@angular/core';
import {AppMessageService, FriendlyMessage} from "../app-message.service";
import {
  trigger,
  state,
  style,
  animate,
  transition,
} from '@angular/animations';


@Component({
  selector: 'app-friendly-messages',
  templateUrl: './friendly-messages.component.html',
  styleUrls: ['./friendly-messages.component.css'],
  animations:[
    trigger('insertRemove', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('0.5s', style({ opacity: 1 })),
      ]),
      transition(':leave', [
        animate('0.5s', style({ opacity: 0 }))
      ])
    ]),
  ]
})
export class FriendlyMessagesComponent implements OnInit {

  @Input()
  public msg: FriendlyMessage;
  @Input()
  public index: number;

  private _intervalId: number;
  private time:number = 0;

  constructor(public appMsgs: AppMessageService) {
  }

  ngOnInit() {
    this._intervalId = setInterval(() => {
      this.time++;
      this.removeMe();
      clearInterval(this._intervalId);
      this._intervalId = null;
    }, this.msg.delay);
  }

  removeMe(){
    this.appMsgs.removeMessage(this.index);
  }

  hover(is:boolean){
  }
}
