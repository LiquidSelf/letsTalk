export class DTO<T>{

  private _data: T;

  constructor(data: T) {
    this._data = data;
  }


  get data(): T {
    return this._data;
  }

  set data(value: T) {
    this._data = value;
  }
}
