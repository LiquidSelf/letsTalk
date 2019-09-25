import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MessagingService } from '../messaging.service';
import { Router } from '@angular/router';
import { AuthService } from "../auth.service";

@Component({
  selector: 'app-wellcome-page',
  templateUrl: './wellcome-page.component.html',
  styleUrls: ['./wellcome-page.component.css']
})
export class WellcomePageComponent implements OnInit {

  constructor(
    private auth: AuthService,
    private route: ActivatedRoute,
    private http: HttpClient,
    private mess_serv: MessagingService,
    private router: Router
  ) {

  }

  ngOnInit() {
  }

  toChat(){
    this.router.navigateByUrl("/chat")
  }

  logoutMethod(){
    this.auth.logout();
  }

  showWellcome(){
    this.router.navigateByUrl("/wellcome");
  }
}
