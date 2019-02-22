import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {CatalogueResponse} from '../dto/CatalogueResponse';

@Injectable()
export class CatalogService {

  constructor(private http: HttpClient) {
  }

  search(search: string): Observable<Map<string, Array<CatalogueResponse>>> {
    return this.http.get<Map<string, Array<CatalogueResponse>>>('/api/search?search=' + search);
  }
}
