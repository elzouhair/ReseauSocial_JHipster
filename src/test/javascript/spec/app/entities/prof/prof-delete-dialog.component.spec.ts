/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ReseausocialjhTestModule } from '../../../test.module';
import { ProfDeleteDialogComponent } from 'app/entities/prof/prof-delete-dialog.component';
import { ProfService } from 'app/entities/prof/prof.service';

describe('Component Tests', () => {
  describe('Prof Management Delete Component', () => {
    let comp: ProfDeleteDialogComponent;
    let fixture: ComponentFixture<ProfDeleteDialogComponent>;
    let service: ProfService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReseausocialjhTestModule],
        declarations: [ProfDeleteDialogComponent]
      })
        .overrideTemplate(ProfDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
