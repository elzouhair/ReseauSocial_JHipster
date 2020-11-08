/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ReseausocialjhTestModule } from '../../../test.module';
import { ProfUpdateComponent } from 'app/entities/prof/prof-update.component';
import { ProfService } from 'app/entities/prof/prof.service';
import { Prof } from 'app/shared/model/prof.model';

describe('Component Tests', () => {
  describe('Prof Management Update Component', () => {
    let comp: ProfUpdateComponent;
    let fixture: ComponentFixture<ProfUpdateComponent>;
    let service: ProfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReseausocialjhTestModule],
        declarations: [ProfUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProfUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Prof(123);
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
        const entity = new Prof();
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
