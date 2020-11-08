export interface ICommentaire {
  id?: number;
  commentaire?: string;
  userLogin?: string;
  userId?: number;
  posteId?: number;
}

export class Commentaire implements ICommentaire {
  constructor(
    public id?: number,
    public commentaire?: string,
    public userLogin?: string,
    public userId?: number,
    public posteId?: number
  ) {}
}
