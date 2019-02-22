import {Component, OnInit} from '@angular/core';
import {CatalogService} from '../service/catalog.service';
import {CatalogueResponse} from '../dto/CatalogueResponse';
import {FormControl} from '@angular/forms';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/map';
import {PersistenceService, StorageType} from 'angular-persistence';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  private static STORAGE_KEY = 'favorites-list';
  searchControl = new FormControl();
  favorites: Map<string, Array<CatalogueResponse>> = new Map();

  content: Map<string, Array<CatalogueResponse>> = new Map();

  constructor(private catalogueService: CatalogService,
              private persistenceService: PersistenceService) { }

  ngOnInit() {
    this.favorites = this.persistenceService.get(MainComponent.STORAGE_KEY, StorageType.SESSION) || new Map();

    this.searchControl.valueChanges
      .debounceTime(500)
      .distinctUntilChanged()
      .map(search => this.search(search))
      .subscribe();
  }

  search(expression: string) {
    this.catalogueService.search(expression).subscribe(
      result => {
        this.content = result;
      },
      error => {
        console.error('Searching failed with error', error);
      });
  }

  getKeys() {
    return new Set([...this.getKeysForMap(this.content), ...this.getKeysForMap(this.favorites)]);
  }

  getKeysForMap(map) {
    return Object.keys(map);
  }
  favorite(key: string, response: CatalogueResponse) {
    const index = this.indexOf(key, response, this.favorites);
    if (index > -1) {
      const valueArray = this.favorites[key];
      valueArray.splice(index, 1);
    } else {
      const valueArray = this.favorites[key];
      valueArray.push(response);
    }
    this.persistenceService.set(MainComponent.STORAGE_KEY, this.favorites, {type: StorageType.SESSION});
  }

  private indexOf(key: string, item: CatalogueResponse, map: Map<string, Array<CatalogueResponse>>) {
    if (!this.favorites[key]) {
      this.favorites[key] = [];
    }
    const list = map[key];
    for (let i = 0; i < list.length; i ++) {
      if (list[i].id === item.id) {
        return i;
      }
    }
    return -1;
  }
}
