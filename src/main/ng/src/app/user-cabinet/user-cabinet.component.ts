import { Component, OnInit } from '@angular/core';
import {WellcomePageComponent} from "../wellcome-page/wellcome-page.component";
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { Inject } from '@angular/core';
import {MatFormFieldModule, MatFormFieldControl} from '@angular/material/form-field';
import {UserProfileDTO} from "../top-bar/UserProfileDTO";

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
    public data: UserProfileDTO
  )
  { }

}
