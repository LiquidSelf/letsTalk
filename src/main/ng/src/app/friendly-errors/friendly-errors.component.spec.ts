import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendlyErrorsComponent } from './friendly-errors.component';

describe('FriendlyErrorsComponent', () => {
  let component: FriendlyErrorsComponent;
  let fixture: ComponentFixture<FriendlyErrorsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FriendlyErrorsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FriendlyErrorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
