import { TestBed, inject } from '@angular/core/testing';

import { CatalogSessionService } from './catalog-session.service';

describe('CatalogSessionService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CatalogSessionService]
    });
  });

  it('should be created', inject([CatalogSessionService], (service: CatalogSessionService) => {
    expect(service).toBeTruthy();
  }));
});
