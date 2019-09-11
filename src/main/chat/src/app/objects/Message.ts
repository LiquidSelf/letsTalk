export class Message{
  message: string;
  login: string;

  constructor(login: string, message: string) {
    this.message = message;
    this.login = login;
  }
}
