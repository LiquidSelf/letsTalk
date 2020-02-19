import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpResponse,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { AuthService } from "./auth.service";
import { tap, map, catchError } from 'rxjs/operators';
import {AppMessageService, MessageColor} from "./app-message.service";
@Injectable()
export class TokenInterseptor implements HttpInterceptor {

  constructor(private auth: AuthService,
              private appMess: AppMessageService) {}
  intercept(req: HttpRequest<any>, next: HttpHandler):
  Observable<HttpEvent<any>>
  {
    if(this.auth.getToken())
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${this.auth.getToken()}`
      }
    });
    return next.handle(req)
      .pipe<any>(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = '';
          if (error.error instanceof ErrorEvent) {
            // client-side error
            errorMessage = `Error: ${error.error.message}`;
          } else {
            // server-side error
            if(error.status === 403){
              this.auth.putToken(null);
            }
            errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
          }
          return throwError(error);
        })
      );
  }
}
