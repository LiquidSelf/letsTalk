export class WebSocketMessage{

  private _data: any;
  private _token: string;
  private _isPing: boolean = false;

  constructor(token: string) {
    this._token = token;
  }


  get isPing(): boolean {
    return this._isPing;
  }

  set isPing(value: boolean) {
    this._isPing = value;
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
