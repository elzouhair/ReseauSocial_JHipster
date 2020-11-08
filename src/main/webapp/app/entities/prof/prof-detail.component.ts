import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProf } from 'app/shared/model/prof.model';

@Component({
  selector: 'jhi-prof-detail',
  templateUrl: './prof-detail.component.html'
})
export class ProfDetailComponent implements OnInit {
  prof: IProf;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prof }) => {
      this.prof = prof;
    });
  }

  previousState() {
    window.history.back();
  }
}
