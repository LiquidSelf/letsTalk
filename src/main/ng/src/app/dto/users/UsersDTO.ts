export class UsersDTO {

  private _exp: number;
  private _iat: number;

  private _username: string;
  private _age: number;
  private _authorities: [];
  private _accountNonExpired:      boolean;
  private _accountNonLocked:       boolean;
  private _credentialsNonExpired:  boolean;
  private _enabled:                boolean;


  get exp(): number {
    return this._exp;
  }

  set exp(value: number) {
    this._exp = value;
  }

  get iat(): number {
    return this._iat;
  }

  set iat(value: number) {
    this._iat = value;
  }

  get username(): string {
    return this._username;
  }

  set username(value: string) {
    this._username = value;
  }

  get age(): number {
    return this._age;
  }

  set age(value: number) {
    this._age = value;
  }

  get authorities(): [] {
    return this._authorities;
  }

  set authorities(value: []) {
    this._authorities = value;
  }

  get accountNonExpired(): boolean {
    return this._accountNonExpired;
  }

  set accountNonExpired(value: boolean) {
    this._accountNonExpired = value;
  }

  get accountNonLocked(): boolean {
    return this._accountNonLocked;
  }

  set accountNonLocked(value: boolean) {
    this._accountNonLocked = value;
  }

  get credentialsNonExpired(): boolean {
    return this._credentialsNonExpired;
  }

  set credentialsNonExpired(value: boolean) {
    this._credentialsNonExpired = value;
  }

  get enabled(): boolean {
    return this._enabled;
  }

  set enabled(value: boolean) {
    this._enabled = value;
  }
}
