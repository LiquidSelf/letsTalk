import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FeedMessageFull } from "../dto/feed/FeedMessageFull";
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import {DTO} from "../dto/DTO";

@Component({
  selector: 'app-feed-details',
  templateUrl: './feed-details.component.html',
  styleUrls: ['./feed-details.component.css']
})
export class FeedDetailsComponent implements OnInit {

  record: FeedMessageFull;

  isLoading: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private http: HttpClient,

  ) { }

  ngOnInit(): void {
    this.getFeedRecord();
  }

  getFeedRecord(){
    const id = +this.route.snapshot.paramMap.get('id');

    this.isLoading = true;

    this.http.get<DTO<FeedMessageFull>>("/api/feed/record/"+id
      ).subscribe(
      (result:DTO<FeedMessageFull>)=>{
        console.log(result);
        this.record = result.data;
      },
      (ex: any)=>{
        console.log('ex',ex);
        // if(ex.status == 403) this.appMess.showUnauthorized();
        // else                 console.log('unexpected exception on get all msgs', ex);

      },
      ()=>{
        this.isLoading = false;
      }
    );
  }
}
