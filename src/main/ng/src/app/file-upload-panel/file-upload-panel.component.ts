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

  loadedImageUrl:string = 'drop.png';

  constructor (private auth: AuthService){

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

        console.log('next',response);
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

  public fileOverAnother(e:any):void {
    this.hasAnotherDropZoneOver = e;
  }
}
