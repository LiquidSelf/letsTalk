import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpParams} from '@angular/common/http' ;
import { Router } from '@angular/router';
import {UsersDTO} from "./dto/users/UsersDTO";
import {LoginPageComponent} from "./login-page/login-page.component";
import {RegistrationPageComponent} from "./registration-page/registration-page.component";
import {AppMessageService, MessageColor} from "./app-message.service";
import { FriendlyMessage } from "./app-message.service";
import {DTO} from "./dto/DTO";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public static origin_url: string = window.location.origin;

  private static USER_TOKEN:string = "saved_token";
  private _user:UsersDTO;

  constructor(
    private http: HttpClient,
    private router: Router,
    private appMsg: AppMessageService,
  ) {
    this.putToken(this.getToken());
  }

  authenticate(credentials, errorCallback:(msg:string) => void) {

    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    this.http.post<DTO<string>>(
      "/authenticate",
      credentials,
      {
        headers:headers
      }
    ).subscribe(
      (next:DTO<string>)=>{
        this.putToken(next.data);
      },
      (error) => {
        this.putToken(null);
        if(error && error.error && error.error.message)
          errorCallback(error.error.message);
        else
          errorCallback(null);
      }
      );
  }

  registrate(newCredentials, callback:(msg:string) => void){
      let headers = new HttpHeaders({
        'Content-Type': 'application/json',
      });

      this.http.post<DTO<string>>(
        "/registration",
        newCredentials,
        {
          headers:headers,
        }
      ).subscribe(
        (next:DTO<string>)=>{
          this.appMsg.showMessage(next.data, 10000, MessageColor._GREEN);
          this.router.navigateByUrl('/wellcome');
        },
        (error) => {
          this.router.navigateByUrl('/registration');
          if(callback) {
            if (error && error.error && error.error.message)
              callback(error.error.message);
            else
              callback(null)
          }
        }
      );
  }

  updateUser(updateMe:UsersDTO, callback:(success:boolean, errorMsg?:string) => void){

    this.http.post<DTO<string>>(
      "/api/users/",
      updateMe,
    ).subscribe(
      (next:DTO<string>)=>{
        if(next) this.putToken(next.data);
        if(!callback) return;
        callback(true);
      },
      (error) => {
        if(!callback) return;
        callback(false, error.error.message);
      }
    );
  }

  parseJwt (token):UsersDTO {
    if(!token) return null;

    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    let user:UsersDTO = JSON.parse(jsonPayload);
    console.log('parseJwt', user);
    return user;
  };

  logout(){
    this.putToken(null);
  }

  get user(): UsersDTO {
    return this._user;
  }

  set user(value: UsersDTO) {
    this._user = value;
  }

  public putToken(token:string, callback?:() => void){
     localStorage.setItem(AuthService.USER_TOKEN, token);
     this.user = this.parseJwt(token);
     if(callback) callback();
  }

  getToken():string{
    let token = localStorage.getItem(AuthService.USER_TOKEN);
    if(!token || token === "null") return null;
    return token;
  }
}
