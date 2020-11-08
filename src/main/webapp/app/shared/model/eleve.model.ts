export interface IEleve {
  id?: number;
  nomEleve?: string;
  prenomEleve?: string;
}

export class Eleve implements IEleve {
  constructor(public id?: number, public nomEleve?: string, public prenomEleve?: string) {}
}
