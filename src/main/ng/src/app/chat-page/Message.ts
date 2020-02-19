export class Message{
  text: string;
  username: string;

  constructor(text: string, username?:string) {
    this.text = text;
    this.username = username;
  }
}
