import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpParams} from '@angular/common/http' ;
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private static USER_TOKEN:string = "saved_token";

  error:any = null;

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
  }

  authenticate(credentials) {

    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    this.http.post<any>(
      "/authenticate",
      credentials,
      {
        headers:headers
      }
    ).subscribe(
      (next)=>{
        this.putToken(next.token);
        this.router.navigateByUrl('/wellcome');
        this.error = null;
      },
      (error) => {
        this.putToken(null);
        this.router.navigateByUrl('/login?error=1');
        this.error = error;
      }
      );
  }

  parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
  };

  logout(){
    let func = ()=>{
      this.putToken(null)
      this.router.navigateByUrl('/login');
    }

    func();
  }

  getSub():string{
    if(!this.getToken()) return null;
    let decoded = this.parseJwt(this.getToken());
    return decoded.sub;
  }

  private putToken(token:string){
     localStorage.setItem(AuthService.USER_TOKEN, token);
  }

  getToken():string{
    let token = localStorage.getItem(AuthService.USER_TOKEN);
    if(!token || token === "null") return null;
    return token;
  }

  wsTempTiket(callback){
    this.http.get('/api/chat/ws_tiket',
      {responseType: 'text'}
    ).subscribe(callback);
  }
}
