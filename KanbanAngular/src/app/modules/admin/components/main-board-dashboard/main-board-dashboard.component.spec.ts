import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainBoardDashboardComponent } from './main-board-dashboard.component';

describe('MainBoardDashboardComponent', () => {
  let component: MainBoardDashboardComponent;
  let fixture: ComponentFixture<MainBoardDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainBoardDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MainBoardDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
