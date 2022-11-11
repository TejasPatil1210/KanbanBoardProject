import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditDialogBoxComponent } from './edit-dialog-box.component';

describe('EditDialogBoxComponent', () => {
  let component: EditDialogBoxComponent;
  let fixture: ComponentFixture<EditDialogBoxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditDialogBoxComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditDialogBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
