import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewDataSetComponent } from './add-new-data-set.component';

describe('AddNewDataSetComponent', () => {
  let component: AddNewDataSetComponent;
  let fixture: ComponentFixture<AddNewDataSetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddNewDataSetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddNewDataSetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
