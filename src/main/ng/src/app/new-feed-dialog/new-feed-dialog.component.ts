import { Component, OnInit, ViewChild } from '@angular/core';
import {FeedMessage} from "../dto/feed/FeedMessage";
import {AppMessageService, MessageColor} from "../app-message.service";
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {MatFormFieldModule, MatFormFieldControl} from '@angular/material/form-field';
import { Inject } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import {FileUploadPanelComponent} from "../file-upload-panel/file-upload-panel.component";

@Component({
  selector: 'app-new-feed-dialog',
  templateUrl: './new-feed-dialog.component.html',
  styleUrls: ['./new-feed-dialog.component.css']
})
export class NewFeedDialogComponent implements OnInit {

  @ViewChild(FileUploadPanelComponent) fileUploadPanel;

  constructor(
    public dialogRef: MatDialogRef<NewFeedDialogComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: FeedMessage,
    private http: HttpClient,
    private appMess: AppMessageService

  ) {}

  ngOnInit(): void {
  }

  onNoClick(){
    this.dialogRef.close();
  }

  onOkClick(){
    this.data.imageUrl = this.fileUploadPanel.loadedImageUrl;
    console.log("data", this.data);

    this.http.put("api/feed/",
      this.data
    ).subscribe(
      (next)=>{
        this.dialogRef.close();
      },
      (error: HttpErrorResponse)=>{
        let errorMsg = error.error.localizedMessage;
        this.appMess.showMessage(errorMsg ? errorMsg : "save error", 10000, MessageColor._YELLOW);
      }
    )

  }

  stopEvent(event){
    event.stopPropagation();
  }

}
