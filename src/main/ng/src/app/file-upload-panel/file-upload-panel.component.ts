import { Component } from '@angular/core';
import { FileUploader, FileUploaderOptions } from 'ng2-file-upload';
import {AuthService} from "../auth.service";

const URL = '/api/uploadFile';

@Component({
  selector: 'app-file-upload-panel',
  templateUrl: './file-upload-panel.component.html',
  styleUrls: ['./file-upload-panel.component.css']
})
export class FileUploadPanelComponent {

  uploader:FileUploader;
  hasBaseDropZoneOver:boolean;
  hasAnotherDropZoneOver:boolean;
  response:string;

  constructor (private auth: AuthService){

    console.log(auth.getToken());


    this.uploader = new FileUploader(this.opts());

    this.uploader.onBeforeUploadItem = (fileItem: any) => {
      this.uploader.setOptions(this.opts());
      console.log(this.uploader);
    };

    this.hasBaseDropZoneOver = false;
    this.hasAnotherDropZoneOver = false;

    this.response = '';

    this.uploader.response.subscribe( res => this.response = res );
  }

  opts():FileUploaderOptions{
    let options:FileUploaderOptions =
      {
        url: URL,
        disableMultipart: false,
        headers: [{ name: 'Authorization', value: 'Bearer '+this.auth.getToken()}]
      };

    return options;
  }


  public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
  }

  public fileOverAnother(e:any):void {
    this.hasAnotherDropZoneOver = e;
  }
}
