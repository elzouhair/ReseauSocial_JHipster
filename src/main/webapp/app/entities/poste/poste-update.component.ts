import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPoste, Poste } from 'app/shared/model/poste.model';
import { PosteService } from './poste.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-poste-update',
  templateUrl: './poste-update.component.html'
})
export class PosteUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    description: [],
    type: [],
    fichier: [],
    fichierContentType: [],
    userId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected posteService: PosteService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ poste }) => {
      this.updateForm(poste);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(poste: IPoste) {
    this.editForm.patchValue({
      id: poste.id,
      description: poste.description,
      type: poste.type,
      fichier: poste.fichier,
      fichierContentType: poste.fichierContentType,
      userId: poste.userId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const poste = this.createFromForm();
    if (poste.id !== undefined) {
      this.subscribeToSaveResponse(this.posteService.update(poste));
    } else {
      this.subscribeToSaveResponse(this.posteService.create(poste));
    }
  }

  private createFromForm(): IPoste {
    return {
      ...new Poste(),
      id: this.editForm.get(['id']).value,
      description: this.editForm.get(['description']).value,
      type: this.editForm.get(['type']).value,
      fichierContentType: this.editForm.get(['fichierContentType']).value,
      fichier: this.editForm.get(['fichier']).value,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoste>>) {
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
}
