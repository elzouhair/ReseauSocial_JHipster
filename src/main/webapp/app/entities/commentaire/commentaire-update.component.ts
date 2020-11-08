import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommentaire, Commentaire } from 'app/shared/model/commentaire.model';
import { CommentaireService } from './commentaire.service';
import { IUser, UserService } from 'app/core';
import { IPoste } from 'app/shared/model/poste.model';
import { PosteService } from 'app/entities/poste';

@Component({
  selector: 'jhi-commentaire-update',
  templateUrl: './commentaire-update.component.html'
})
export class CommentaireUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  postes: IPoste[];

  editForm = this.fb.group({
    id: [],
    commentaire: [],
    userId: [null, Validators.required],
    posteId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected commentaireService: CommentaireService,
    protected userService: UserService,
    protected posteService: PosteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ commentaire }) => {
      this.updateForm(commentaire);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.posteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPoste[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPoste[]>) => response.body)
      )
      .subscribe((res: IPoste[]) => (this.postes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(commentaire: ICommentaire) {
    this.editForm.patchValue({
      id: commentaire.id,
      commentaire: commentaire.commentaire,
      userId: commentaire.userId,
      posteId: commentaire.posteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const commentaire = this.createFromForm();
    if (commentaire.id !== undefined) {
      this.subscribeToSaveResponse(this.commentaireService.update(commentaire));
    } else {
      this.subscribeToSaveResponse(this.commentaireService.create(commentaire));
    }
  }

  private createFromForm(): ICommentaire {
    return {
      ...new Commentaire(),
      id: this.editForm.get(['id']).value,
      commentaire: this.editForm.get(['commentaire']).value,
      userId: this.editForm.get(['userId']).value,
      posteId: this.editForm.get(['posteId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommentaire>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackPosteById(index: number, item: IPoste) {
    return item.id;
  }
}
