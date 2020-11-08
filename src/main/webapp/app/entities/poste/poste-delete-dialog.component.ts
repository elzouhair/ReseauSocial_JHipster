import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPoste } from 'app/shared/model/poste.model';
import { PosteService } from './poste.service';

@Component({
  selector: 'jhi-poste-delete-dialog',
  templateUrl: './poste-delete-dialog.component.html'
})
export class PosteDeleteDialogComponent {
  poste: IPoste;

  constructor(protected posteService: PosteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.posteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'posteListModification',
        content: 'Deleted an poste'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-poste-delete-popup',
  template: ''
})
export class PosteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ poste }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PosteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.poste = poste;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/poste', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/poste', { outlets: { popup: null } }]);
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
