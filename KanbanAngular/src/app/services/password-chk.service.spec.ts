import { TestBed } from '@angular/core/testing';

import { PasswordChkService } from './password-chk.service';

describe('PasswordChkService', () => {
  let service: PasswordChkService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PasswordChkService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
