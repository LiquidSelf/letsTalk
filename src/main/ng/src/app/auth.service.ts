import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpParams} from '@angular/common/http' ;
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private static USER_TOKEN:string = "saved_user";
  principal: any;

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.principal = this.pullUser();
  }

  mee(callback?){
    this.http.get("/api/mee").subscribe(next=>{
      this.putUser(next);
      callback(next);
    })
  }

  authenticate(credentials, callback?) {

    let headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    }
      );

    const params = new HttpParams({
      fromObject: credentials
    });

    this.http.post<any>(
      "/login",
      params,
      {
        headers:headers
      }
    ).subscribe(
      (next)=>{
        this.putUser(next);
        this.router.navigateByUrl('/wellcome');
      },
      (error) => {
        callback(error.error.localizedMessage);
        this.putUser(null)
        this.router.navigateByUrl('/login?error=1');
      }
      );
  }

  logout(){
    let func = (e)=>{
      this.putUser(null)
      this.router.navigateByUrl('/login');
    }

    this.http.post("/logout","").subscribe(
      func,
      func
    );
  }

  isAuthenticated():boolean{
     if(this.principal) return true;
     return false;
  }

  private putUser(user){
    localStorage.setItem(AuthService.USER_TOKEN, JSON.stringify(user));
    this.principal = user;
  }

  private pullUser():any{
    let st:string = localStorage.getItem(AuthService.USER_TOKEN);
    if(!st) return null;
    return JSON.parse(st);
  }
}
