import {MessageType} from "../messaging.service";

export class WebSocketMessage{

  private _data: any;
  private _token: string;
  private _messageType: MessageType;

  constructor(token: string, type: MessageType) {
    this._data = null;
    this._messageType = null;
    this._token = token;
    this._messageType = type;
  }


  get messageType(): MessageType {
    return this._messageType;
  }

  set messageType(value: MessageType) {
    this._messageType = value;
  }

  get data(): any {
    return this._data;
  }

  set data(value: any) {
    this._data = value;
  }

  get token(): string {
    return this._token;
  }

  set token(value: string) {
    this._token = value;
  }
}
