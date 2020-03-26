import { FileUploader, FileUploaderOptions } from 'ng2-file-upload';
import {AuthService} from "../auth.service";
import { Component, OnInit, Output, EventEmitter, ViewChild, ElementRef } from '@angular/core';

const URL = '/api/uploadFile';

@Component({
  selector: 'app-file-upload-panel',
  templateUrl: './file-upload-panel.component.html',
  styleUrls: ['./file-upload-panel.component.css']
})
export class FileUploadPanelComponent {

  @ViewChild('choose_button')
  public choiseButton: ElementRef;

  uploader:FileUploader;
  hasBaseDropZoneOver:boolean;
  hasAnotherDropZoneOver:boolean;
  response:string;

  loadedImageUrl:string;

  // @Output() notify: EventEmitter<string> = new EventEmitter<string>();

  constructor (
    private auth: AuthService
  ){

    this.uploader = new FileUploader(this.opts());

    this.uploader.onBeforeUploadItem = (fileItem: any) => {
      this.uploader.setOptions(this.opts());
    };

    this.hasBaseDropZoneOver = false;
    this.hasAnotherDropZoneOver = false;

    this.response = '';

    this.uploader.response.subscribe( res => {

        if(!(typeof res === 'string')) return;

        let response = JSON.parse(res);

        if(response.data){
          console.log('next',response.data);
          this.loadedImageUrl = response.data;
        }
      },
      err =>{
       console.log("error", err)
      });
  }

  opts():FileUploaderOptions{
    let options:FileUploaderOptions =
      {
        url: URL,
        autoUpload: true,
        disableMultipart: false,
        headers: [{ name: 'Authorization', value: 'Bearer '+this.auth.getToken()}]
      };

    return options;
  }


  public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
  }

  chooseFile(){
    this.choiseButton.nativeElement.click();
  }
}
