export class DTO{

  private _data: any;

  constructor(data: any) {
    this._data = data;
  }


  get data(): any {
    return this._data;
  }

  set data(value: any) {
    this._data = value;
  }
}
