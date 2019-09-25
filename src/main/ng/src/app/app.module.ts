import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ChatPageComponent } from './chat-page/chat-page.component';
import { WellcomePageComponent } from './wellcome-page/wellcome-page.component';
import { HttpClientModule }    from '@angular/common/http';
import { LoginPageComponent } from './login-page/login-page.component';
import { TokenInterseptor } from "./interseptor";
import { HTTP_INTERCEPTORS } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    ChatPageComponent,
    WellcomePageComponent,
    LoginPageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterseptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
