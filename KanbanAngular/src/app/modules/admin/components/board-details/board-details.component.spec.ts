import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardDetailsComponent } from './board-details.component';

describe('BoardDetailsComponent', () => {
  let component: BoardDetailsComponent;
  let fixture: ComponentFixture<BoardDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoardDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
