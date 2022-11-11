import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskDialogBoxComponent } from './task-dialog-box.component';

describe('TaskDialogBoxComponent', () => {
  let component: TaskDialogBoxComponent;
  let fixture: ComponentFixture<TaskDialogBoxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TaskDialogBoxComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaskDialogBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
