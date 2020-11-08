/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ReseausocialjhTestModule } from '../../../test.module';
import { PosteUpdateComponent } from 'app/entities/poste/poste-update.component';
import { PosteService } from 'app/entities/poste/poste.service';
import { Poste } from 'app/shared/model/poste.model';

describe('Component Tests', () => {
  describe('Poste Management Update Component', () => {
    let comp: PosteUpdateComponent;
    let fixture: ComponentFixture<PosteUpdateComponent>;
    let service: PosteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReseausocialjhTestModule],
        declarations: [PosteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PosteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PosteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PosteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Poste(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Poste();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
