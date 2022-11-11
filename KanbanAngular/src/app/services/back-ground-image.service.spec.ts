import { TestBed } from '@angular/core/testing';

import { BackGroundImageService } from './back-ground-image.service';

describe('BackGroundImageService', () => {
  let service: BackGroundImageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BackGroundImageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
