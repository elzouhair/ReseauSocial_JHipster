import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ReseausocialjhSharedModule } from 'app/shared';
import {
  PosteComponent,
  PosteDetailComponent,
  PosteUpdateComponent,
  PosteDeletePopupComponent,
  PosteDeleteDialogComponent,
  posteRoute,
  postePopupRoute
} from './';

const ENTITY_STATES = [...posteRoute, ...postePopupRoute];

@NgModule({
  imports: [ReseausocialjhSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PosteComponent, PosteDetailComponent, PosteUpdateComponent, PosteDeleteDialogComponent, PosteDeletePopupComponent],
  entryComponents: [PosteComponent, PosteUpdateComponent, PosteDeleteDialogComponent, PosteDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReseausocialjhPosteModule {}
