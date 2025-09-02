import { TestBed } from '@angular/core/testing';

import { Loanservice } from './loan-service';

describe('Loanservice', () => {
  let service: Loanservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Loanservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});