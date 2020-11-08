export interface IProf {
  id?: number;
  nomProf?: string;
  prenomProf?: string;
}

export class Prof implements IProf {
  constructor(public id?: number, public nomProf?: string, public prenomProf?: string) {}
}
