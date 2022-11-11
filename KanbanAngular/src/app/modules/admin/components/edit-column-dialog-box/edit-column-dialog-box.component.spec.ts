import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditColumnDialogBoxComponent } from './edit-column-dialog-box.component';

describe('EditColumnDialogBoxComponent', () => {
  let component: EditColumnDialogBoxComponent;
  let fixture: ComponentFixture<EditColumnDialogBoxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditColumnDialogBoxComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditColumnDialogBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
