import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProf, Prof } from 'app/shared/model/prof.model';
import { ProfService } from './prof.service';

@Component({
  selector: 'jhi-prof-update',
  templateUrl: './prof-update.component.html'
})
export class ProfUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nomProf: [],
    prenomProf: []
  });

  constructor(protected profService: ProfService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ prof }) => {
      this.updateForm(prof);
    });
  }

  updateForm(prof: IProf) {
    this.editForm.patchValue({
      id: prof.id,
      nomProf: prof.nomProf,
      prenomProf: prof.prenomProf
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const prof = this.createFromForm();
    if (prof.id !== undefined) {
      this.subscribeToSaveResponse(this.profService.update(prof));
    } else {
      this.subscribeToSaveResponse(this.profService.create(prof));
    }
  }

  private createFromForm(): IProf {
    return {
      ...new Prof(),
      id: this.editForm.get(['id']).value,
      nomProf: this.editForm.get(['nomProf']).value,
      prenomProf: this.editForm.get(['prenomProf']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProf>>) {
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
