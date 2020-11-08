import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPoste } from 'app/shared/model/poste.model';
import { ICommentaire } from 'app/shared/model/commentaire.model';
import { CommentaireService } from 'app/entities/commentaire';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-poste-detail',
  templateUrl: './poste-detail.component.html'
})
export class PosteDetailComponent implements OnInit {
  poste: IPoste;
  commentaires: ICommentaire[];

  constructor(
    protected commentaireService: CommentaireService,
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute
  ) {
    this.commentaires = [];
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ poste }) => {
      this.poste = poste;
    });
    this.loadAllComments();
  }
  loadAllComments() {
    this.commentaireService.query().subscribe((res: HttpResponse<ICommentaire[]>) => (this.commentaires = res.body));
    console.log('commentaires : ' + this.commentaires);
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
