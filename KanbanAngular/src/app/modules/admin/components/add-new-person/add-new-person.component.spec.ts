import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewPersonComponent } from './add-new-person.component';

describe('AddNewPersonComponent', () => {
  let component: AddNewPersonComponent;
  let fixture: ComponentFixture<AddNewPersonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddNewPersonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddNewPersonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
