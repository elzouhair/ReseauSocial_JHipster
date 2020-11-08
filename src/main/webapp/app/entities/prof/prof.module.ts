import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ReseausocialjhSharedModule } from 'app/shared';
import {
  ProfComponent,
  ProfDetailComponent,
  ProfUpdateComponent,
  ProfDeletePopupComponent,
  ProfDeleteDialogComponent,
  profRoute,
  profPopupRoute
} from './';

const ENTITY_STATES = [...profRoute, ...profPopupRoute];

@NgModule({
  imports: [ReseausocialjhSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ProfComponent, ProfDetailComponent, ProfUpdateComponent, ProfDeleteDialogComponent, ProfDeletePopupComponent],
  entryComponents: [ProfComponent, ProfUpdateComponent, ProfDeleteDialogComponent, ProfDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReseausocialjhProfModule {}
