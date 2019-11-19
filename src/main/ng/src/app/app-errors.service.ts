import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppErrorsService {

  public _msgs: ErrorMessage[] = new Array();
  private _intervalId: number;
  private time:number = 0;
  private _isPaused:boolean = false;

  private lastRemoved: ErrorMessage = null;

  constructor() {
    this.newMessage(new ErrorMessage("test messages"))
    this.newMessage(new ErrorMessage("err2or"))
    this.newMessage(new ErrorMessage("err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2orerr2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2orerr2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or err2orerr2orerr2orerr2or"))
    this.newMessage(new ErrorMessage("error 4 o 4"))
  }

  rmvn(delay:number){
    this._intervalId = setInterval(() => {
      if(!this._isPaused) {
        this.time++;
        this.lastRemoved = this._msgs.shift();


        if (this._intervalId && this._msgs && this._msgs.length === 0) {
          clearInterval(this._intervalId);
          this._intervalId = null;
        }
      }

    }, delay);
  }

  newMessage(message:ErrorMessage){
    this._msgs.push(message);
    if(!this._intervalId)
    this.rmvn(2000);
  }

  set isPaused(value: boolean) {
    if (this.lastRemoved) {
      this._msgs.unshift(this.lastRemoved);
      this.lastRemoved = null;
    }
    this._isPaused = value;
  }
}


export class ErrorMessage{
  private _value:string;


  constructor(message: string) {
    this._value = message;
  }

  get value(): string {
    return this._value;
  }

  set value(value: string) {
    this._value = value;
  }
}
