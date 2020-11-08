/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ReseausocialjhTestModule } from '../../../test.module';
import { PosteDeleteDialogComponent } from 'app/entities/poste/poste-delete-dialog.component';
import { PosteService } from 'app/entities/poste/poste.service';

describe('Component Tests', () => {
  describe('Poste Management Delete Component', () => {
    let comp: PosteDeleteDialogComponent;
    let fixture: ComponentFixture<PosteDeleteDialogComponent>;
    let service: PosteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReseausocialjhTestModule],
        declarations: [PosteDeleteDialogComponent]
      })
        .overrideTemplate(PosteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PosteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PosteService);
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
