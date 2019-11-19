import { TestBed } from '@angular/core/testing';

import { FastTravelService } from './fast-travel.service';

describe('FastTravelService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FastTravelService = TestBed.get(FastTravelService);
    expect(service).toBeTruthy();
  });
});
