import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FileUploadPanelComponent } from './file-upload-panel.component';

describe('FileUploadPanelComponent', () => {
  let component: FileUploadPanelComponent;
  let fixture: ComponentFixture<FileUploadPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FileUploadPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FileUploadPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
