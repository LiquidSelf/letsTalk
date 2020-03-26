import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ChatPageComponent } from './chat-page/chat-page.component';
import { WellcomePageComponent } from './wellcome-page/wellcome-page.component';
import { HttpClientModule }    from '@angular/common/http';
import { LoginPageComponent } from './login-page/login-page.component';
import { TokenInterseptor } from "./interseptor";
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TopBarComponent } from './top-bar/top-bar.component';
import { LeftBarComponent } from './left-bar/left-bar.component';
import { FriendlyMessagesComponent } from './friendly-messages/friendly-messages.component';
import { MatDialogModule } from "@angular/material/dialog";
import { UserCabinetComponent} from "./user-cabinet/user-cabinet.component";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { RegistrationPageComponent } from './registration-page/registration-page.component';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { DragDropModule } from '@angular/cdk/drag-drop';
import {
  VgBufferingModule,
  VgCoreModule,
  VgOverlayPlayModule,
  VgControlsModule }
  from 'ngx-videogular';
import { FileUploadPanelComponent } from './file-upload-panel/file-upload-panel.component';
import { FileSelectDirective, FileUploadModule } from 'ng2-file-upload';
import { FeedComponent } from './feed/feed.component';
import { NewFeedDialogComponent } from './new-feed-dialog/new-feed-dialog.component';
import { FeedDetailsComponent } from './feed-details/feed-details.component';

@NgModule({
  declarations: [
    AppComponent,
    ChatPageComponent,
    WellcomePageComponent,
    LoginPageComponent,
    TopBarComponent,
    LeftBarComponent,
    FriendlyMessagesComponent,
    UserCabinetComponent,
    RegistrationPageComponent,
    FileUploadPanelComponent,
    FeedComponent,
    NewFeedDialogComponent,
    FeedDetailsComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    ScrollingModule,
    FormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    VgBufferingModule,
    VgCoreModule,
    VgControlsModule,
    VgOverlayPlayModule,
    FileUploadModule,
    DragDropModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterseptor, multi: true }
  ],
  bootstrap: [AppComponent],
  entryComponents: [UserCabinetComponent]
})
export class AppModule { }
