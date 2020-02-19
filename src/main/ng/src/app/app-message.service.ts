import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class AppMessageService {

  public _msgs: FriendlyMessage[] = new Array();
  private _intervalId: number;
  private time:number = 0;

  private lastRemoved: FriendlyMessage = null;
  private lastRemovedIndex: number = 0;

  constructor() {
    this.showMessage("test messages",1500, MessageColor._BLUE);
    this.showMessage("test messages",5990, MessageColor._YELLOW);
    this.showMessage("test messages",3500, MessageColor._GREEN);
    this.showMessage("test messages",1000, MessageColor._RED);
  }

  rmvn(delay:number){
    this._intervalId = setInterval(() => {
        this.time++;
        this.lastRemoved = this._msgs.shift();

        if (this._intervalId && this._msgs && this._msgs.length === 0) {
          clearInterval(this._intervalId);
          this._intervalId = null;
      }

    }, delay);
  }

  showMessage(text:string, delay:number = 2000, color: MessageColor = MessageColor._BLUE){
    if(text){
      let message:FriendlyMessage = new FriendlyMessage(text, color, delay, this);
      this._msgs.push(message);

      //удаляет все сообщения
      // if(!this._intervalId)
      // this.rmvn(delay);
    }
  }

  showUnauthorized(){
    this.showMessage(`You are not logged in, please login.`, 10000, MessageColor._RED);
  }

  removeMessage(index:number){
    this.lastRemoved = this._msgs.splice(index,1)[0];
    this.lastRemovedIndex = index;
  }

  // set isPaused(value: boolean) {
  //   if (this.lastRemoved) {
  //     this._msgs.unshift(this.lastRemoved);
  //     this.lastRemoved = null;
  //   }
  //   this._isPaused = value;
  // }
}

export enum MessageColor{
  _RED    =  '_RED',
  _BLUE   =  '_BLUE',
  _YELLOW =  '_YELLOW',
  _GREEN  =  '_GREEN',
}

export class FriendlyMessage {

  private _value: string;
  private _color: MessageColor;
  private _delay: number;
  private _caller:AppMessageService;


  constructor(value: string, color: MessageColor, delay: number, caller:AppMessageService) {
    this._value = value;
    this._color = color;
    this._delay = delay;
    this._caller = caller;
  }

  startTimer(){

  }

  get value(): string {
    return this._value;
  }

  set value(value: string) {
    this._value = value;
  }

  get color(): MessageColor {
    return this._color;
  }

  set color(value: MessageColor) {
    this._color = value;
  }

  get delay(): number {
    return this._delay;
  }

  set delay(value: number) {
    this._delay = value;
  }
}


