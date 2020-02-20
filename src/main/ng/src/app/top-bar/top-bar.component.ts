import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MessagingService } from '../messaging.service';
import { Router } from '@angular/router';
import { AuthService } from "../auth.service";
import { WellcomePageComponent } from "../wellcome-page/wellcome-page.component";
import { MatDialog } from '@angular/material/dialog';
import { UserCabinetComponent } from "../user-cabinet/user-cabinet.component";
import { UsersDTO } from "../dto/users/UsersDTO";

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit {

  constructor(
    public auth: AuthService,
    private route: ActivatedRoute,
    private http: HttpClient,
    private mess_serv: MessagingService,
    private router: Router,
    private welcPage:WellcomePageComponent,
    public dialog: MatDialog

  ) { }

  ngOnInit() {
  }

  editProfile(){
    if(!this.auth.user) return;

    let dialogObject = {...this.auth.user};

    const dialog = this.dialog.open(UserCabinetComponent, {
      width: '500px',
      data: dialogObject
    });
  }
}
