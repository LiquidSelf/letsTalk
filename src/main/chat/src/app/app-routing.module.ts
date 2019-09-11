import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChatPageComponent} from './chat-page/chat-page.component';
import { WellcomePageComponent} from './wellcome-page/wellcome-page.component';
import {LoginPageComponent} from "./login-page/login-page.component";


const routes: Routes = [
  {path: 'chat', component: ChatPageComponent},
  {path: 'wellcome', component: WellcomePageComponent},
  {path: '', redirectTo: 'wellcome', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{ useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
