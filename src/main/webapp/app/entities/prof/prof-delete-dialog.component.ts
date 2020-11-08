import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProf } from 'app/shared/model/prof.model';
import { ProfService } from './prof.service';

@Component({
  selector: 'jhi-prof-delete-dialog',
  templateUrl: './prof-delete-dialog.component.html'
})
export class ProfDeleteDialogComponent {
  prof: IProf;

  constructor(protected profService: ProfService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.profService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'profListModification',
        content: 'Deleted an prof'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-prof-delete-popup',
  template: ''
})
export class ProfDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prof }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProfDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.prof = prof;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/prof', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/prof', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
