/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReseausocialjhTestModule } from '../../../test.module';
import { ProfDetailComponent } from 'app/entities/prof/prof-detail.component';
import { Prof } from 'app/shared/model/prof.model';

describe('Component Tests', () => {
  describe('Prof Management Detail Component', () => {
    let comp: ProfDetailComponent;
    let fixture: ComponentFixture<ProfDetailComponent>;
    const route = ({ data: of({ prof: new Prof(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReseausocialjhTestModule],
        declarations: [ProfDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProfDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prof).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
