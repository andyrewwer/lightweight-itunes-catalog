export class CatalogueResponse {

  id: number;
  name: string;
  artwork: string;
  genre: string;
  url: string;

  constructor(args: CatalogueResponse = <CatalogueResponse>{}) {
    this.id = args.id;
    this.name = args.name;
    this.artwork = args.artwork;
    this.genre = args.genre;
    this.url = args.url;
  }
}
