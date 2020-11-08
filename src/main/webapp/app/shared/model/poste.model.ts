import { ICommentaire } from 'app/shared/model/commentaire.model';

export const enum Type {
  COURS = 'COURS',
  TP = 'TP',
  EXAMEN = 'EXAMEN'
}

export interface IPoste {
  id?: number;
  description?: string;
  type?: Type;
  fichierContentType?: string;
  fichier?: any;
  userLogin?: string;
  userId?: number;
  commentaires?: ICommentaire[];
}

export class Poste implements IPoste {
  constructor(
    public id?: number,
    public description?: string,
    public type?: Type,
    public fichierContentType?: string,
    public fichier?: any,
    public userLogin?: string,
    public userId?: number,
    public commentaires?: ICommentaire[]
  ) {}
}
