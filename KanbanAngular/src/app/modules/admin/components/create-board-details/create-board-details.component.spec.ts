import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateBoardDetailsComponent } from './create-board-details.component';

describe('CreateBoardDetailsComponent', () => {
  let component: CreateBoardDetailsComponent;
  let fixture: ComponentFixture<CreateBoardDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateBoardDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateBoardDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
