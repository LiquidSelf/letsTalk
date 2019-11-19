import { Component, OnInit } from '@angular/core';
import { Renderer } from '@angular/core';
import { AppErrorsService } from "../app-errors.service";
import {
  trigger,
  state,
  style,
  animate,
  transition,
} from '@angular/animations';


@Component({
  selector: 'app-friendly-errors',
  templateUrl: './friendly-errors.component.html',
  styleUrls: ['./friendly-errors.component.css'],
  animations:[
    trigger('insertRemove', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('0.5s', style({ opacity: 1 })),
      ]),
      transition(':leave', [
        animate('0.5s', style({ opacity: 0 }))
      ])
    ]),
  ]
})
export class FriendlyErrorsComponent implements OnInit {

  constructor(private appErrs: AppErrorsService) {
  }

  ngOnInit() {
  }

  removeMe(index:number){
    this.appErrs._msgs.splice(index,1);
  }

  hover(is:boolean){
    this.appErrs.isPaused = is;
  }
}
