import { Component, OnInit } from '@angular/core';
import {WellcomePageComponent} from "../wellcome-page/wellcome-page.component";
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { Inject } from '@angular/core';
import {MatFormFieldModule, MatFormFieldControl} from '@angular/material/form-field';
import {UsersDTO} from "../dto/users/UsersDTO";
import {AuthService} from "../auth.service";
import {AppMessageService} from "../app-message.service";

@Component({
  selector: 'app-user-cabinet',
  templateUrl: './user-cabinet.component.html',
  styleUrls: ['./user-cabinet.component.css']
})
export class UserCabinetComponent{

  constructor
  (
    public dialogRef: MatDialogRef<UserCabinetComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: UsersDTO,
    private auth: AuthService,
    private appMess: AppMessageService
  )
  { }

  onNoClick(){
    this.dialogRef.close();
  }

  onOkClick(){
    this.auth.updateUser(this.data, (success:boolean, errorMsg:string)=>{
      if(success) this.dialogRef.close();
      else{
        if(errorMsg)
        this.appMess.showMessage(errorMsg)
        else
        this.appMess.showMessage("error on save")
      }

    });
  }

}
