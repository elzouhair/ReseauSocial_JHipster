import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ReseausocialjhSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [ReseausocialjhSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [ReseausocialjhSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReseausocialjhSharedModule {
  static forRoot() {
    return {
      ngModule: ReseausocialjhSharedModule
    };
  }
}
