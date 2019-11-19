import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MessagingService } from '../messaging.service';
import { Router } from '@angular/router';
import { AuthService } from "../auth.service";
import { WellcomePageComponent } from "../wellcome-page/wellcome-page.component";
import { MatDialog } from '@angular/material/dialog';
import { UserCabinetComponent } from "../user-cabinet/user-cabinet.component";
import { UserProfileDTO } from "./UserProfileDTO";

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit {

  constructor(
    private auth: AuthService,
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
    const dialog = this.dialog.open(UserCabinetComponent, {
      width: '250px',
      data: new UserProfileDTO(1,"test", 23)
    });

    dialog.afterClosed().subscribe(result=>{
      console.log(result);
    });
  }
}
