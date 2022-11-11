import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColumnDialogBoxComponent } from './column-dialog-box.component';

describe('ColumnDialogBoxComponent', () => {
  let component: ColumnDialogBoxComponent;
  let fixture: ComponentFixture<ColumnDialogBoxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ColumnDialogBoxComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ColumnDialogBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
