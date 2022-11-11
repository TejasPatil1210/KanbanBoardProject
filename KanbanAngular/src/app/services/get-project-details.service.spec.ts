import { TestBed } from '@angular/core/testing';

import { GetProjectDetailsService } from './get-project-details.service';

describe('GetProjectDetailsService', () => {
  let service: GetProjectDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetProjectDetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
