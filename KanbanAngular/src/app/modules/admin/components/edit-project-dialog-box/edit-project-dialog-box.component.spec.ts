import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditProjectDialogBoxComponent } from './edit-project-dialog-box.component';

describe('EditProjectDialogBoxComponent', () => {
  let component: EditProjectDialogBoxComponent;
  let fixture: ComponentFixture<EditProjectDialogBoxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditProjectDialogBoxComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditProjectDialogBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
