import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ReseausocialjhSharedModule } from 'app/shared';
import {
  EleveComponent,
  EleveDetailComponent,
  EleveUpdateComponent,
  EleveDeletePopupComponent,
  EleveDeleteDialogComponent,
  eleveRoute,
  elevePopupRoute
} from './';

const ENTITY_STATES = [...eleveRoute, ...elevePopupRoute];

@NgModule({
  imports: [ReseausocialjhSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [EleveComponent, EleveDetailComponent, EleveUpdateComponent, EleveDeleteDialogComponent, EleveDeletePopupComponent],
  entryComponents: [EleveComponent, EleveUpdateComponent, EleveDeleteDialogComponent, EleveDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReseausocialjhEleveModule {}
