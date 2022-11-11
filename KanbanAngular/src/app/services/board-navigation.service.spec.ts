import { TestBed } from '@angular/core/testing';

import { BoardNavigationService } from './board-navigation.service';

describe('BoardNavigationService', () => {
  let service: BoardNavigationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BoardNavigationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
