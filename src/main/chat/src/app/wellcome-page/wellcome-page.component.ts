import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MessagingService } from '../messaging.service';

@Component({
  selector: 'app-wellcome-page',
  templateUrl: './wellcome-page.component.html',
  styleUrls: ['./wellcome-page.component.css']
})
export class WellcomePageComponent implements OnInit {

  login:string = 'unknown'

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private mess_serv: MessagingService
  ) { }

  ngOnInit() {

  }

}
