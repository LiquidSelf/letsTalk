import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChatPageComponent} from './chat-page/chat-page.component';
import { WellcomePageComponent} from './wellcome-page/wellcome-page.component';
import { LoginPageComponent} from "./login-page/login-page.component";
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';

const routes: Routes = [
  {path: 'chat', component: ChatPageComponent},
  {path: 'wellcome', component: WellcomePageComponent},
  {path: 'login', component: LoginPageComponent},
  {path: '', redirectTo: 'wellcome', pathMatch: 'full' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes,{ useHash: true}),
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  // providers: [AppService],
  bootstrap: [AppComponent],
  exports: [RouterModule]
})
export class AppRoutingModule { }
