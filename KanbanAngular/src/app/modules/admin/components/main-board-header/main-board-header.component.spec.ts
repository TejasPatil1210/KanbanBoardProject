import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainBoardHeaderComponent } from './main-board-header.component';

describe('MainBoardHeaderComponent', () => {
  let component: MainBoardHeaderComponent;
  let fixture: ComponentFixture<MainBoardHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainBoardHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MainBoardHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
