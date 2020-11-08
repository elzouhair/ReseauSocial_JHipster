/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReseausocialjhTestModule } from '../../../test.module';
import { PosteDetailComponent } from 'app/entities/poste/poste-detail.component';
import { Poste } from 'app/shared/model/poste.model';

describe('Component Tests', () => {
  describe('Poste Management Detail Component', () => {
    let comp: PosteDetailComponent;
    let fixture: ComponentFixture<PosteDetailComponent>;
    const route = ({ data: of({ poste: new Poste(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReseausocialjhTestModule],
        declarations: [PosteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PosteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PosteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.poste).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
