import { Component, OnInit } from '@angular/core';
import {FeedMessage} from "../dto/FeedMessage";
import {AppMessageService} from "../app-message.service";
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {MatFormFieldModule, MatFormFieldControl} from '@angular/material/form-field';
import { Inject } from '@angular/core';

@Component({
  selector: 'app-new-feed-dialog',
  templateUrl: './new-feed-dialog.component.html',
  styleUrls: ['./new-feed-dialog.component.css']
})
export class NewFeedDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<NewFeedDialogComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: FeedMessage,
    private appMess: AppMessageService
  ) { }

  ngOnInit(): void {
  }

  onNoClick(){
    this.dialogRef.close();
  }

  onOkClick(){
    // this.auth.updateUser(this.data, (success:boolean, errorMsg:string)=>{
    //   if(success) this.dialogRef.close();
    //   else{
    //     if(errorMsg)
    //       this.appMess.showMessage(errorMsg)
    //     else
    //       this.appMess.showMessage("error on save")
    //   }

    // });
  }

}
