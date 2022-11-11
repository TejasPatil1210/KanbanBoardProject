import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateBoardHeaderComponent } from './create-board-header.component';

describe('CreateBoardHeaderComponent', () => {
  let component: CreateBoardHeaderComponent;
  let fixture: ComponentFixture<CreateBoardHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateBoardHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateBoardHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
