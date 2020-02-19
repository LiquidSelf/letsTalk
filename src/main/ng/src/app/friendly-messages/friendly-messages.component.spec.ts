import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendlyMessagesComponent } from './friendly-messages.component';

describe('FriendlyMessagesComponent', () => {
  let component: FriendlyMessagesComponent;
  let fixture: ComponentFixture<FriendlyMessagesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FriendlyMessagesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FriendlyMessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
