import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEleve, Eleve } from 'app/shared/model/eleve.model';
import { EleveService } from './eleve.service';

@Component({
  selector: 'jhi-eleve-update',
  templateUrl: './eleve-update.component.html'
})
export class EleveUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nomEleve: [],
    prenomEleve: []
  });

  constructor(protected eleveService: EleveService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ eleve }) => {
      this.updateForm(eleve);
    });
  }

  updateForm(eleve: IEleve) {
    this.editForm.patchValue({
      id: eleve.id,
      nomEleve: eleve.nomEleve,
      prenomEleve: eleve.prenomEleve
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const eleve = this.createFromForm();
    if (eleve.id !== undefined) {
      this.subscribeToSaveResponse(this.eleveService.update(eleve));
    } else {
      this.subscribeToSaveResponse(this.eleveService.create(eleve));
    }
  }

  private createFromForm(): IEleve {
    return {
      ...new Eleve(),
      id: this.editForm.get(['id']).value,
      nomEleve: this.editForm.get(['nomEleve']).value,
      prenomEleve: this.editForm.get(['prenomEleve']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEleve>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
