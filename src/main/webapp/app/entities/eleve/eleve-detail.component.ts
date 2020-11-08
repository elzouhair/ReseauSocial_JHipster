import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEleve } from 'app/shared/model/eleve.model';

@Component({
  selector: 'jhi-eleve-detail',
  templateUrl: './eleve-detail.component.html'
})
export class EleveDetailComponent implements OnInit {
  eleve: IEleve;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ eleve }) => {
      this.eleve = eleve;
    });
  }

  previousState() {
    window.history.back();
  }
}
